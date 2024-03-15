package com.kliachenko.data.dashboard

import com.kliachenko.data.dashboard.cache.currencyPair.CurrentTimeInMillis
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class CurrencyPairCacheTest {

    private lateinit var currentTimeMillis: FakeCurrentTimeMillis

    @Before
    fun setup() {
        currentTimeMillis = FakeCurrentTimeMillis()
    }

    @Test
    fun testValidRateAndTime() {
        val validTime = System.currentTimeMillis() - 23 * 3600 * 1000
        val actual = IsInvalidCacheRateAndTime.Base(currentTimeMillis)
        Assert.assertFalse(actual.isInvalid(0.1, validTime))
    }

    @Test
    fun testInvalidRateAndTime() {
        val invalidTime = System.currentTimeMillis() - 25 * 3600 * 1000
        val actual = IsInvalidCacheRateAndTime.Base(currentTimeMillis)
        Assert.assertTrue(actual.isInvalid(0.0, invalidTime))
    }

    @Test
    fun testInvalidTime() {
        val invalidTime = System.currentTimeMillis() - 25 * 3600 * 1000
        val actual = IsInvalidCacheRateAndTime.Base(currentTimeMillis)
        Assert.assertTrue(actual.isInvalid(0.1, invalidTime))
    }

    @Test
    fun testInvalidRate() {
        val validTime = System.currentTimeMillis() - 23 * 3600 * 1000
        val actual = IsInvalidCacheRateAndTime.Base(currentTimeMillis)
        Assert.assertTrue(actual.isInvalid(0.0, validTime))
    }

    private class FakeCurrentTimeMillis : CurrentTimeInMillis {
        override fun currentTime(): Long {
            return System.currentTimeMillis()
        }

    }
}

