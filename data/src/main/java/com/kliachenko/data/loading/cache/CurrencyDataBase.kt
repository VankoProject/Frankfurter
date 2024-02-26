package com.kliachenko.data.loading.cache

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CurrencyCache::class], version = 1, exportSchema = false)
abstract class CurrencyDataBase : RoomDatabase() {

    abstract fun currencyDao(): CurrencyDao
}