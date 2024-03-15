package com.kliachenko.data.dashboard

import com.kliachenko.domain.dashboard.DashBoardItem
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class DashboardItemMapperTest {

    private lateinit var updatedRate: FakeUpdateRate
    private lateinit var isInvalidCacheRateAndTime: FakeIsInvalidCacheRateAndTime
    private lateinit var mapper: DashboardItemMapper

    @Before
    fun setup() {
        updatedRate = FakeUpdateRate()
        isInvalidCacheRateAndTime = FakeIsInvalidCacheRateAndTime()
        mapper = DashboardItemMapper(
            updatedRate = updatedRate,
            isInvalidCacheTime = isInvalidCacheRateAndTime
        )
    }

    @Test
    fun testMapValid() = runBlocking {
        isInvalidCacheRateAndTime.validRate()
        val expected: DashBoardItem = DashBoardItem.Base(
            fromCurrency = "A", toCurrency = "B", rate = 1.0
        )
        val actual: DashBoardItem = mapper.map("A", "B", 1.0, 1L)
        Assert.assertEquals(expected, actual)
        updatedRate.checkUpdateCalled(false)
    }

    @Test
    fun testMapInvalid() = runBlocking {
        isInvalidCacheRateAndTime.inValidRate()
        val expected: DashBoardItem = DashBoardItem.Base(
            fromCurrency = "A", toCurrency = "B", rate = 1.0
        )
        val actual: DashBoardItem = mapper.map("A", "B", 0.0, 1L)
        Assert.assertEquals(expected, actual)
        updatedRate.checkUpdateCalled(true)
        updatedRate.checkUpdateParams("A", "B")
    }
}

private class FakeUpdateRate : UpdatedRate {

    private val actualRate: Double = 1.0
    private var isCalled = false
    private var from = ""
    private var to = ""

    override suspend fun updatedRate(from: String, to: String): Double {
        this.from = from
        this.to = to
        isCalled = true
        return actualRate
    }

    fun checkUpdateCalled(expected: Boolean) {
        Assert.assertTrue(expected == isCalled)
    }

    fun checkUpdateParams(from: String, to: String) {
        Assert.assertEquals(from, this.from)
        Assert.assertEquals(to, this.to)
    }

}

private class FakeIsInvalidCacheRateAndTime : IsInvalidCacheRateAndTime {

    private var isValid: Boolean = false

    override fun isInvalid(rate: Double, time: Long): Boolean {
        return isValid
    }

    fun inValidRate() {
        isValid = true
    }

    fun validRate() {
        isValid = false
    }

}