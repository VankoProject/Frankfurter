package com.kliachenko.presentation.load

import com.kliachenko.domain.LoadResult
import com.kliachenko.domain.MainRepository
import org.junit.Assert

class FakeRepository: MainRepository {

    private var actualCacheData: LoadResult = LoadResult.Empty

    fun noCacheData() {
        actualCacheData = LoadResult.Empty
    }

    fun checkLoadData(expected: LoadResult.Success) {
        Assert.assertEquals(expected, actualCacheData)
    }

    fun hasCacheData(): LoadResult {
        return LoadResult.Success(listOf(CurrencyModel("A", "A")))
    }

    override suspend fun loadCurrencies(): LoadResult {
        actualCacheData = LoadResult.Success(listOf(CurrencyModel("A", "A")))
        return actualCacheData
    }

    override suspend fun hasCurrencies(): Boolean {
        return true
    }

    override suspend fun currencies(): List<CurrencyModel> {
        return listOf(CurrencyModel("A", "A"))
    }
}