package com.kliachenko.data.loading.cache

import javax.inject.Inject

interface CurrencyCacheDataSource {

    interface Save {
        suspend fun save(currencies: List<CurrencyCache>)
    }

    interface Read {
        suspend fun read(): List<CurrencyCache>
    }

    interface Mutable : Save, Read

    class Base @Inject constructor(private val currencyDao: CurrencyDao) : Mutable {

        constructor(
            database: CurrencyDataBase
        ) : this(
            database.currencyDao()
        )

        override suspend fun save(currencies: List<CurrencyCache>) {
            currencyDao.saveCurrencies(currencies)
        }

        override suspend fun read() =  currencyDao.currencies()
    }
}