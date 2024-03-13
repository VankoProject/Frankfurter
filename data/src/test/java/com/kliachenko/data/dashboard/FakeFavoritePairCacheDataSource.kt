package com.kliachenko.data.dashboard

import com.kliachenko.data.dashboard.cache.currencyPair.CurrencyPair
import com.kliachenko.data.dashboard.cache.currencyPair.FavoritePairCacheDataSource
import org.junit.Assert

class FakeFavoritePairCacheDataSource : FavoritePairCacheDataSource.Mutable {

    private var actualListCurrencyPair: MutableList<CurrencyPair> = mutableListOf()

    override suspend fun favoriteCurrencyPairs(): List<CurrencyPair> {
        return actualListCurrencyPair
    }

    override suspend fun saveFavoritePair(currencyPair: CurrencyPair) {
        actualListCurrencyPair.add(currencyPair)
    }

    override suspend fun removeCurrencyPair(currencyPair: CurrencyPair) {
        actualListCurrencyPair.remove(currencyPair)
    }

    fun emptyList() {
        actualListCurrencyPair = mutableListOf()
    }

    fun hasData() {
        actualListCurrencyPair = mutableListOf(
            CurrencyPair(fromCurrency = "A", toCurrency = "B", rate = 1.0, time = 1),
            CurrencyPair(fromCurrency = "C", toCurrency = "D", rate = 1.0, time = 1)
        )
    }

    fun checkSaveData(updatePair: CurrencyPair) {
        Assert.assertEquals(updatePair, actualListCurrencyPair[0])
    }

    fun checkSaved(expected: List<CurrencyPair>) {
        Assert.assertEquals(expected, actualListCurrencyPair)
    }

    fun checkAfterRemove(vararg pairs: CurrencyPair) {
        Assert.assertEquals(pairs.toList(), actualListCurrencyPair)
    }

}