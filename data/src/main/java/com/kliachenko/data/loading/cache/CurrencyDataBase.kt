package com.kliachenko.data.loading.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kliachenko.data.dashboard.cache.CurrencyPair
import com.kliachenko.data.dashboard.cache.CurrencyPairDao

@Database(entities = [CurrencyCache::class, CurrencyPair::class], version = 1, exportSchema = false)
abstract class CurrencyDataBase : RoomDatabase() {

    abstract fun currencyDao(): CurrencyDao

    abstract fun currencyPairDao(): CurrencyPairDao
}