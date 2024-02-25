package com.kliachenko.domain

interface MainRepository {

    suspend fun loadCurrencies(): LoadResult



}

interface LoadResult {

    fun map(mapper: Mapper)

    interface Mapper {

        fun mapSuccess(data: List<CurrencyModel>)

        fun mapError(message: String)
    }

    object Success : LoadResult {
        override fun map(mapper: Mapper) = Unit
    }

    data class Error(private val message: String) : LoadResult {
        override fun map(mapper: Mapper) {
            mapper.mapError(message)
        }

    }
}