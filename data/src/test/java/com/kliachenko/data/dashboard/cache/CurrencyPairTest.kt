package com.kliachenko.data.dashboard.cache

import org.junit.Assert
import org.junit.Test

class CurrencyPairTest {


    @Test
    fun invalidRate() {
        val currencyPair = CurrencyPair("A", "B", rate = 0.0)
        val actual: Boolean =
            currencyPair.isInvalid(currentTimeInMillis = FakeCurrentTimeMillis(1000))
        Assert.assertTrue(actual)
    }

    @Test
    fun invalidTime() {
        val currencyPair = CurrencyPair("A", "B", time = 1000)
        val actual: Boolean =
            currencyPair.isInvalid(currentTimeInMillis = FakeCurrentTimeMillis(2000))
        Assert.assertTrue(actual)
    }

    @Test
    fun invalidRateAndTime() {
        val currencyPair = CurrencyPair("A", "B", rate = 0.0, time = 1000)
        val actual: Boolean =
            currencyPair.isInvalid(currentTimeInMillis = FakeCurrentTimeMillis(2000))
        Assert.assertTrue(actual)
    }

    @Test
    fun notInvalid() {
        val currencyPair = CurrencyPair("A", "B", rate = 1.0, time = 1000)
        val actual: Boolean =
            currencyPair.isInvalid(currentTimeInMillis = FakeCurrentTimeMillis(2000))
        Assert.assertFalse(actual)
    }

    private class FakeCurrentTimeMillis(private val time: Long) : CurrentTimeInMillis {
        override fun currentTime(): Long {
            return time
        }

    }
}

