package com.kliachenko.data.dashboard.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CurrencyPairDao {

    @Query("select * from currency_pair")
    suspend fun favoriteCurrencyPair(): List<CurrencyPair>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrencyPair(currencyPair: CurrencyPair)
}