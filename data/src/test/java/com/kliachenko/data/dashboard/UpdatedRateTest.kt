package com.kliachenko.data.dashboard

import com.kliachenko.data.dashboard.cache.CurrencyPair
import com.kliachenko.data.dashboard.cache.CurrentTimeInMillis
import com.kliachenko.data.dashboard.cloud.CurrencyRateCloudDataSource
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class UpdatedRateTest {

    private lateinit var cacheDataSource: FakeFavoritePairCacheDataSource
    private lateinit var rateCloudDataSource: FakeCurrencyRateCloudDataSource
    private lateinit var currentTimeInMillis: FakeCurrentTimeInMillis
    private lateinit var updatedRate: UpdatedRate

    @Before
    fun setup() {
        cacheDataSource = FakeFavoritePairCacheDataSource()
        rateCloudDataSource = FakeCurrencyRateCloudDataSource()
        currentTimeInMillis = FakeCurrentTimeInMillis()
        updatedRate = UpdatedRate.Base(
            cacheDataSource = cacheDataSource,
            rateCloudDataSource = rateCloudDataSource,
            currentTimeInMillis = currentTimeInMillis
        )
    }

    @Test
    fun testSaveUpdateRate() = runBlocking {
        val expectedRate = 1.5
        rateCloudDataSource.expectRate(rate = expectedRate)
        val actual = updatedRate.updatedRate(
            CurrencyPair("A", "B")
        )
        Assert.assertEquals(expectedRate, actual, 0.01)
        val expectedUpdatedRate = CurrencyPair(
            "A", "B", 1.5, 999
        )
        cacheDataSource.checkSaveData(expectedUpdatedRate)
        rateCloudDataSource.check("A", "B")

    }

}

private class FakeCurrentTimeInMillis : CurrentTimeInMillis {

    override fun currentTime(): Long {
        return 999
    }

}

private class FakeCurrencyRateCloudDataSource : CurrencyRateCloudDataSource {

    private var actualCloudRate: Double = 0.0
    private var from: String = ""
    private var to: String = ""

    override suspend fun rate(fromCurrency: String, toCurrency: String): Double {
        from = fromCurrency
        to = toCurrency
        return actualCloudRate
    }

    fun expectRate(rate: Double) {
        actualCloudRate = rate
    }

    fun check(from: String, to: String) {
        Assert.assertEquals(from, this.from)
        Assert.assertEquals(to, this.to)
    }

}