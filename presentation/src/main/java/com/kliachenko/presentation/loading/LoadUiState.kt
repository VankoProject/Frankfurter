package com.kliachenko.presentation.loading

interface LoadUiState {

    fun update()

    object Progress: LoadUiState {
        override fun update() {
            TODO("Not yet implemented")
        }
    }

    data class Error(private val message: String): LoadUiState {
        override fun update() {
            TODO("Not yet implemented")
        }

    }

    object Empty: LoadUiState {
        override fun update() = Unit

    }
}
