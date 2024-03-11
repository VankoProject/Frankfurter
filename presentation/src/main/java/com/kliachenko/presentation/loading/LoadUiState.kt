package com.kliachenko.presentation.loading

import com.kliachenko.presentation.core.views.ChangeVisibility
import com.kliachenko.presentation.core.views.ErrorText

interface LoadUiState {

    fun update(retryButton: ChangeVisibility, progressBar: ChangeVisibility, textView: ErrorText)

    data class Error(private val message: String) : LoadUiState {
        override fun update(
            retryButton: ChangeVisibility,
            progressBar: ChangeVisibility,
            textView: ErrorText,
        ) {
            retryButton.show()
            progressBar.hide()
            textView.show()
            textView.change(text = message)
        }
    }

    object Progress : LoadUiState {
        override fun update(
            retryButton: ChangeVisibility,
            progressBar: ChangeVisibility,
            textView: ErrorText,
        ) {
            retryButton.hide()
            textView.hide()
            progressBar.show()
        }
    }

    object Empty : LoadUiState {
        override fun update(
            retryButton: ChangeVisibility,
            progressBar: ChangeVisibility,
            textView: ErrorText,
        ) = Unit
    }
}
