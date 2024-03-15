package com.kliachenko.data.dashboard.cache.currencyPair

import javax.inject.Inject

interface FavoritePairCacheDataSource {

    interface Save {
        suspend fun saveFavoritePair(currencyPairCache: CurrencyPairCache)
    }

    interface Read {
        suspend fun favoriteCurrencyPairs(): List<CurrencyPairCache>
    }

    interface Remove {
        suspend fun removeCurrencyPair(currencyPairCache: CurrencyPairCache)
    }

    interface Mutable : Read, Save, Remove

    class Base @Inject constructor(
        private val dao: CurrencyPairDao,
    ) : Mutable {
        override suspend fun favoriteCurrencyPairs(): List<CurrencyPairCache> =
            dao.favoriteCurrencyPair()

        override suspend fun saveFavoritePair(currencyPairCache: CurrencyPairCache) {
            dao.insertCurrencyPair(currencyPairCache)
        }

        override suspend fun removeCurrencyPair(currencyPairCache: CurrencyPairCache) {
            dao.removeCurrencyPair(currencyPairCache)
        }
    }
}