package com.kliachenko.data.dashboard.cache

interface FavoritePairCacheDataSource {

    interface Save {
        suspend fun saveFavoritePair(currencyPair: CurrencyPair)
    }

    interface Read {
        suspend fun favoriteCurrencyPairs(): List<CurrencyPair>
    }

    interface Mutable: Read, Save

    class Base(
        private val dao: CurrencyPairDao,
    ) : Mutable {
        override suspend fun favoriteCurrencyPairs(): List<CurrencyPair> =
            dao.favoriteCurrencyPair()

        override suspend fun saveFavoritePair(currencyPair: CurrencyPair) {
            dao.insertCurrencyPair(currencyPair)
        }
    }
}