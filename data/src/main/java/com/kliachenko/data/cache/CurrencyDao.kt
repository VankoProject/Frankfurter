package com.kliachenko.data.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CurrencyDao {

    @Query("select * from currencies_table")
    suspend fun currencies(): List<CurrencyCache>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCurrencies(currencies: List<CurrencyCache>)
}
