package com.kliachenko.data.dashboard.cache

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "currency_pair", primaryKeys = ["from_currency", "toCurrency"])
data class CurrencyPair(
    @ColumnInfo(name = "from_currency")
    val fromCurrency: String,
    @ColumnInfo(name = "to_currency")
    val toCurrency: String,
    @ColumnInfo(name = "rate")
    val rate: Double = 0.0,
    @ColumnInfo(name = "time")
    val time: Long,
) {

    fun isInvalid(currentTimeInMillis: CurrentTimeInMillis): Boolean {
        return (currentTimeInMillis.currentTime() - time > 24 * 3600 * 1000) || rate == 0.0
    }
}