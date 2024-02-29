package com.kliachenko.presentation.dashboard

import android.widget.ProgressBar

interface SettingsUiState {

    fun update(progressBar: ProgressBar, )

    object Empty: SettingsUiState {
        override fun update() {
            TODO("Not yet implemented")
        }
    }



}
