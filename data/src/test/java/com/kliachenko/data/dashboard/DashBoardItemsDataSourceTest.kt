package com.kliachenko.data.dashboard

import com.kliachenko.data.dashboard.cache.currencyPair.CurrencyPair
import com.kliachenko.data.dashboard.cache.currencyPair.CurrencyPairCache
import com.kliachenko.domain.dashboard.DashBoardItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class DashBoardItemsDataSourceTest {

    private lateinit var dashBoardItemsDataSource: DashBoardItemsDataSource
    private lateinit var dispatcher: CoroutineDispatcher
    private lateinit var mapper: FakeMapper

    @Before
    fun setup() {
        dispatcher = Dispatchers.Unconfined
        mapper = FakeMapper()
        dashBoardItemsDataSource = DashBoardItemsDataSource.Base(
            dispatcher = dispatcher,
            mapper = mapper
        )
    }

    @Test
    fun testValidCurrencyPair() = runBlocking {
        val expected = listOf<DashBoardItem>(
            DashBoardItem.Base(fromCurrency = "USD", toCurrency = "EUR", rate = 1.0),
            DashBoardItem.Base(fromCurrency = "USD", toCurrency = "JPY", rate = 1.5),
        )
        val actual = dashBoardItemsDataSource.dashboardItems(
            listOf(
                CurrencyPairCache(fromCurrency = "USD", toCurrency = "EUR", rate = 1.0, time = 1L),
                CurrencyPairCache(fromCurrency = "USD", toCurrency = "JPY", rate = 1.5, time = 1L)
            )
        )
        assertEquals(expected, actual)
    }

}


class FakeMapper : CurrencyPair.Mapper<DashBoardItem> {
    override suspend fun map(from: String, to: String, rate: Double, time: Long): DashBoardItem {
        return DashBoardItem.Base(
            fromCurrency = from,
            toCurrency = to,
            rate = rate
        )
    }
}