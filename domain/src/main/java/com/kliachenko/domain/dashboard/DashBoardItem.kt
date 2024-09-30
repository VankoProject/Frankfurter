package com.kliachenko.domain.dashboard

interface DashBoardItem {

    fun <T: Any> map (mapper: Mapper<T>): T

    interface Mapper<T: Any> {
        fun mapItems(fromCurrency: String, toCurrency: String, rate: Double): T
    }

    data class Base(
        private val fromCurrency: String,
        private val toCurrency: String,
        private val rate: Double
    ): DashBoardItem {
        override fun <T : Any> map(mapper: Mapper<T>): T =
            mapper.mapItems(fromCurrency, toCurrency, rate)
    }
}
