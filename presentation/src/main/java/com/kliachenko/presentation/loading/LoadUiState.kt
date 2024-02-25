package com.kliachenko.presentation.loading

interface LoadUiState {

    fun map(mapper: Mapper)

    interface Mapper {

        fun mapSuccess(data: List<String>)

        fun mapError(message: String)
    }

    object Progress: LoadUiState {
        override fun map(mapper: Mapper) = Unit
    }

    data class Error(private val message: String): LoadUiState {
        override fun map(mapper: Mapper) {
            mapper.mapError(message)
        }
    }

    data class Success (private val data: List<String>): LoadUiState {
        override fun map(mapper: Mapper) {
            mapper.mapSuccess(data)
        }
    }

    object Empty: LoadUiState {
        override fun map(mapper: Mapper) = Unit
    }
}
