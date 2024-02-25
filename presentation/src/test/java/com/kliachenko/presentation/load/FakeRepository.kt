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
        return LoadResult.Success
    }

    override suspend fun loadCurrencies(): LoadResult {
        actualCacheData = LoadResult.Success
        return actualCacheData
    }

    override suspend fun hasCurrencies(): Boolean {
        return true
    }
}