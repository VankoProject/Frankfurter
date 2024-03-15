package com.kliachenko.data.dashboard.cache.currencyPair

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CurrencyPairDao {

    @Query("select * from currency_pair")
    suspend fun favoriteCurrencyPair(): List<CurrencyPairCache>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrencyPair(currencyPairCache: CurrencyPairCache)

    @Delete
    suspend fun removeCurrencyPair(currencyPairCache: CurrencyPairCache)
}