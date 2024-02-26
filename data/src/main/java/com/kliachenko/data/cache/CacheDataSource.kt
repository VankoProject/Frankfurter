package com.kliachenko.data.cache

import android.content.Context
import androidx.room.Room

interface CacheDataSource {

    interface Save {
        suspend fun saveCurrency(currencies: List<CurrencyCache>)
    }

    interface Read {
        suspend fun currencies(): List<CurrencyCache>
    }

    interface Mutable : Save, Read

    class Base(private val currencyDao: CurrencyDao) : Mutable {

        constructor(
            context: Context,
        ) : this(
            Room.databaseBuilder(
                context,
                CurrencyDataBase::class.java,
                "currencyDataBase"
            ).build().currencyDao()
        )

        override suspend fun saveCurrency(currencies: List<CurrencyCache>) {
            currencyDao.saveCurrencies(currencies)
        }

        override suspend fun currencies() =  currencyDao.currencies()
    }
}