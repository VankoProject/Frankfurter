package com.kliachenko.presentation.load

import com.kliachenko.domain.LoadCurrenciesRepository
import com.kliachenko.domain.LoadCurrenciesResult
import org.junit.Assert

class FakeRepository: LoadCurrenciesRepository {

    private var actualCacheData: LoadCurrenciesResult = LoadCurrenciesResult.Empty

    fun noCacheData() {
        actualCacheData = LoadCurrenciesResult.Empty
    }

    fun checkLoadData(expected: LoadCurrenciesResult.Success) {
        Assert.assertEquals(expected, actualCacheData)
    }

    fun hasCacheData(): LoadCurrenciesResult {
        return LoadCurrenciesResult.Success
    }

    override suspend fun loadCurrencies(): LoadCurrenciesResult {
        actualCacheData = LoadCurrenciesResult.Success
        return actualCacheData
    }
}