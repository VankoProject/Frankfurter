package com.kliachenko.data.dashboard

import com.kliachenko.data.dashboard.cache.currencyPair.CurrencyPairCache
import com.kliachenko.data.dashboard.cache.currencyPair.FavoritePairCacheDataSource
import org.junit.Assert

class FakeFavoritePairCacheDataSource : FavoritePairCacheDataSource.Mutable {

    private var actualListCurrencyPairCache: MutableList<CurrencyPairCache> = mutableListOf()

    override suspend fun favoriteCurrencyPairs(): List<CurrencyPairCache> {
        return actualListCurrencyPairCache
    }

    override suspend fun saveFavoritePair(currencyPairCache: CurrencyPairCache) {
        actualListCurrencyPairCache.add(currencyPairCache)
    }

    override suspend fun removeCurrencyPair(currencyPairCache: CurrencyPairCache) {
        actualListCurrencyPairCache.remove(currencyPairCache)
    }

    fun emptyList() {
        actualListCurrencyPairCache = mutableListOf()
    }

    fun hasData() {
        actualListCurrencyPairCache = mutableListOf(
            CurrencyPairCache(fromCurrency = "A", toCurrency = "B"),
            CurrencyPairCache(fromCurrency = "C", toCurrency = "D")
        )
    }

    fun checkSaveData(updatePair: CurrencyPairCache) {
        Assert.assertEquals(updatePair, actualListCurrencyPairCache[0])
    }

    fun checkSaved(expected: List<CurrencyPairCache>) {
        Assert.assertEquals(expected, actualListCurrencyPairCache)
    }

}