package com.kliachenko.presentation.loading

interface LoadUiState {

    fun map(mapper: UiMapper)

    interface UiMapper {

        fun mapError(message: String)
    }

    data class Error(private val message: String): LoadUiState {
        override fun map(mapper: UiMapper) {
            mapper.mapError(message)
        }
    }

    object Progress: LoadUiState {
        override fun map(mapper: UiMapper) = Unit
    }

    object Success: LoadUiState {
        override fun map(mapper: UiMapper) = Unit
    }

    object Empty: LoadUiState {
        override fun map(mapper: UiMapper) = Unit
    }
}
