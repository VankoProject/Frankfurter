package com.kliachenko.data.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import retrofit2.http.GET

@Dao
interface CurrencyDao {

    @GET("select * from currencies_table")
    suspend fun currencies(): List<CurrencyCache>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCurrencies(currencies: List<CurrencyCache>)
}
