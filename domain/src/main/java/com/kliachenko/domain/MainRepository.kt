package com.kliachenko.domain

interface MainRepository {

    suspend fun loadCurrencies(): LoadResult

    suspend fun hasCurrencies(): Boolean

    suspend fun currencies(): List<CurrencyModel>

}

interface LoadResult {

    fun map(mapper: Mapper)

    interface Mapper {

        fun mapSuccess(data: List<CurrencyModel>)

        fun mapError(message: String)
    }

    data class Success(private val data: List<CurrencyModel>) : LoadResult {
        override fun map(mapper: Mapper) {
            mapper.mapSuccess(data)
        }
    }

    data class Error(private val message: String) : LoadResult {
        override fun map(mapper: Mapper) {
            mapper.mapError(message)
        }
    }

    object Empty: LoadResult {
        override fun map(mapper: Mapper) = Unit
    }
}