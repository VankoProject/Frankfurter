package com.kliachenko.data.dashboard.cache.currencyPair

import javax.inject.Inject

interface FavoritePairCacheDataSource {

    interface Save {
        suspend fun saveFavoritePair(currencyPair: CurrencyPair)
    }

    interface Read {
        suspend fun favoriteCurrencyPairs(): List<CurrencyPair>
    }

    interface Remove {
        suspend fun removeCurrencyPair(currencyPair: CurrencyPair)
    }

    interface Mutable : Read, Save, Remove

    class Base @Inject constructor(
        private val dao: CurrencyPairDao,
    ) : Mutable {
        override suspend fun favoriteCurrencyPairs(): List<CurrencyPair> =
            dao.favoriteCurrencyPair()

        override suspend fun saveFavoritePair(currencyPair: CurrencyPair) {
            dao.insertCurrencyPair(currencyPair)
        }

        override suspend fun removeCurrencyPair(currencyPair: CurrencyPair) {
            dao.removeCurrencyPair(currencyPair)
        }
    }
}