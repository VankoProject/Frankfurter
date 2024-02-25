package com.kliachenko.presentation.load

import com.kliachenko.presentation.loading.MainRepository
import org.junit.Assert

class FakeRepository: MainRepository {

    private var actualCacheData: List<String> = emptyList()

    override fun currencies(): List<String> {
        actualCacheData = listOf("A", "B", "C")
        return actualCacheData
    }

    fun noCacheData() {
        actualCacheData = emptyList()
    }

    fun checkLoadData(expected: List<String>) {
        Assert.assertEquals(expected, actualCacheData)
    }

    fun hasCacheData(): List<String> {
        return actualCacheData
    }
}