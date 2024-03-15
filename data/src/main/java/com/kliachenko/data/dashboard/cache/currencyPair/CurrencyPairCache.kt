package com.kliachenko.data.dashboard.cache.currencyPair

import androidx.room.ColumnInfo
import androidx.room.Entity

interface CurrencyPair {

    suspend fun <T : Any> map(mapper: Mapper<T>): T

    interface Mapper<T : Any> {

        suspend fun map(from: String, to: String, rate: Double, time: Long): T
    }
}

@Entity(tableName = "currency_pair", primaryKeys = ["from_currency", "to_currency"])
data class CurrencyPairCache(
    @ColumnInfo(name = "from_currency")
    val fromCurrency: String,
    @ColumnInfo(name = "to_currency")
    val toCurrency: String,
    @ColumnInfo(name = "rate")
    val rate: Double = 0.0,
    @ColumnInfo(name = "time")
    val time: Long = -1,
) : CurrencyPair {

    override suspend fun <T : Any> map(mapper: CurrencyPair.Mapper<T>): T {
        return mapper.map(fromCurrency, toCurrency, rate, time)
    }
}