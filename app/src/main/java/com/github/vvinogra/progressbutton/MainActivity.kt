package com.github.vvinogra.progressbutton

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.github.vvinogra.progressbutton.view.ProgressButtonState
import com.github.vvinogra.progressbutton.view.ProgressButtonView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        setContentView(R.layout.activity_main)

        setButtonOnClickListener(R.id.progress_button_view_default_style)
        setButtonOnClickListener(R.id.progress_button_view_default_theme)
        setButtonOnClickListener(R.id.progress_button_view_primary)
        setButtonOnClickListener(R.id.progress_button_view_primary_variant)
        setButtonOnClickListener(R.id.progress_button_view_outlined)
        setButtonOnClickListener(R.id.progress_button_view_outlined_variant)
    }

    private fun setButtonOnClickListener(@IdRes buttonId: Int) {
        findViewById<ProgressButtonView>(buttonId).let { progressButton ->
            progressButton.setOnClickListener {
                progressButton.startDoingSomeBackgroundWork()
            }
        }
    }

    private fun ProgressButtonView.startDoingSomeBackgroundWork() {
        lifecycleScope.launch {
            setProgressButtonState(ProgressButtonState.IN_PROGRESS)

            // Doing some background work
            delay(5000)

            setProgressButtonState(ProgressButtonState.IDLE)
        }
    }
}