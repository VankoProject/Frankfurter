package com.kliachenko.data.dashboard.cache.currencyCache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kliachenko.data.dashboard.cache.currencyPair.CurrencyPairCache
import com.kliachenko.data.dashboard.cache.currencyPair.CurrencyPairDao

@Database(entities = [CurrencyCache::class, CurrencyPairCache::class], version = 1, exportSchema = false)
abstract class CurrencyDataBase : RoomDatabase() {

    abstract fun currencyDao(): CurrencyDao

    abstract fun currencyPairDao(): CurrencyPairDao
}