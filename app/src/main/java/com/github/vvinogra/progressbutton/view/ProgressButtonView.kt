package com.github.vvinogra.progressbutton.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import com.github.vvinogra.progressbutton.R
import com.google.android.material.theme.overlay.MaterialThemeOverlay

private const val DEF_STYLE_RES = R.style.Widget_App_ProgressButtonView_DefaultStyle

class ProgressButtonView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.progressButtonViewDefaultStyle
) : FrameLayout(
    MaterialThemeOverlay.wrap(
        context,
        attrs,
        defStyleAttr,
        DEF_STYLE_RES
    ),
    attrs,
    defStyleAttr
) {

    private var buttonText: String?

    private val button: Button
    private val progressBar: ProgressBar

    init {
        // Ensure we are using the correctly themed context rather than the context that was passed in.
        val context = getContext()

        val rootView = LayoutInflater.from(context)
            .inflate(R.layout.progress_button_view, this, true)

        button = rootView.findViewById(R.id.button)
        progressBar = rootView.findViewById(R.id.progress_bar)

        val attr = context.obtainStyledAttributes(
            attrs,
            R.styleable.ProgressButtonView,
            defStyleAttr,
            DEF_STYLE_RES
        )

        try {
            buttonText = attr.getString(R.styleable.ProgressButtonView_android_text)
            button.text = buttonText
            isEnabled = attr.getBoolean(R.styleable.ProgressButtonView_android_enabled, true)
        } finally {
            attr.recycle()
        }
    }

    override fun setOnClickListener(listener: OnClickListener?) {
        super.setOnClickListener(listener)
        button.setOnClickListener {
            listener?.onClick(it)
        }
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        button.isEnabled = enabled
    }

    fun setButtonText(@StringRes textRes: Int) {
        setButtonText(resources.getString(textRes))
    }

    fun setButtonText(text: String?) {
        buttonText = text
        setTextVisibility(isVisible = !progressBar.isVisible)
    }

    fun setProgressButtonState(state: ProgressButtonState) {
        when (state) {
            ProgressButtonState.IDLE -> {
                progressBar.isVisible = false
                setTextVisibility(true)
                isEnabled = true
            }
            ProgressButtonState.IN_PROGRESS -> {
                progressBar.isVisible = true
                setTextVisibility(false)
                isEnabled = false
            }
            ProgressButtonState.DISABLED -> {
                progressBar.isVisible = false
                setTextVisibility(true)
                isEnabled = false
            }
        }
    }

    private fun setTextVisibility(isVisible: Boolean) {
        if (isVisible) {
            button.text = buttonText
        } else {
            button.text = ""
        }
    }
}

enum class ProgressButtonState {
    IDLE, IN_PROGRESS, DISABLED
}