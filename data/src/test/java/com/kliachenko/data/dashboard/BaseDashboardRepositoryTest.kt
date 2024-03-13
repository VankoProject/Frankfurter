package com.kliachenko.data.dashboard

import com.kliachenko.data.core.HandleError
import com.kliachenko.data.dashboard.cache.currencyCache.CurrencyCache
import com.kliachenko.data.dashboard.cache.currencyCache.CurrencyCacheDataSource
import com.kliachenko.data.dashboard.cache.currencyPair.CurrencyPair
import com.kliachenko.data.dashboard.cloud.currencyLoad.LoadCurrencyCloudDataSource
import com.kliachenko.domain.dashboard.DashBoardItem
import com.kliachenko.domain.dashboard.DashboardRepository
import com.kliachenko.domain.dashboard.DashboardResult
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.net.UnknownHostException

class BaseDashboardRepositoryTest {

    private lateinit var favoriteCacheDataSource: FakeFavoritePairCacheDataSource
    private lateinit var dashBoardItemsDataSource: FakeDashBoardItemsDataSource
    private lateinit var handleError: FakeHandleError
    private lateinit var dashboardRepository: DashboardRepository
    private lateinit var loadCurrencyCloudDataSource: FakeLoadCurrencyCloudDataSource
    private lateinit var currencyCacheDataSource: FakeCurrencyCacheDataSource

    @Before
    fun setup() {
        favoriteCacheDataSource = FakeFavoritePairCacheDataSource()
        dashBoardItemsDataSource = FakeDashBoardItemsDataSource()
        handleError = FakeHandleError()
        loadCurrencyCloudDataSource = FakeLoadCurrencyCloudDataSource()
        currencyCacheDataSource = FakeCurrencyCacheDataSource()
        dashboardRepository = BaseDashboardRepository(
            loadCurrencyCloudDataSource = loadCurrencyCloudDataSource,
            currencyCacheDataSource = currencyCacheDataSource,
            favoriteCacheDataSource = favoriteCacheDataSource,
            dashBoardItemsDataSource = dashBoardItemsDataSource,
            handleError = handleError
        )
    }

    @Test
    fun testFirstRunEmptyError() = runBlocking {
        currencyCacheDataSource.cacheEmpty()
        loadCurrencyCloudDataSource.errorLoadResult(exception = UnknownHostException())
        val expected: DashboardResult =
            DashboardResult.Error(UnknownHostException().javaClass.name.toString())
        val actual: DashboardResult = dashboardRepository.dashboardItems()
        assertEquals(expected, actual)
    }

    @Test
    fun testFirstRunBothEmpty() = runBlocking {
        currencyCacheDataSource.cacheEmpty()
        loadCurrencyCloudDataSource.successLoadResult()
        favoriteCacheDataSource.emptyList()
        val expected: DashboardResult = DashboardResult.Empty
        val actual: DashboardResult = dashboardRepository.dashboardItems()
        assertEquals(expected, actual)
    }

    @Test
    fun testNotFirstRunSuccess() = runBlocking {
        currencyCacheDataSource.cacheNotEmpty()
        favoriteCacheDataSource.hasData()
        val expected: DashboardResult = DashboardResult.Success(
            listOf(
                DashBoardItem.Base("A", "B", 1.0),
                DashBoardItem.Base("C", "D", 1.0)
            )
        )
        val actual: DashboardResult = dashboardRepository.dashboardItems()
        assertEquals(expected, actual)
    }

    @Test
    fun testNotFirstRunError() = runBlocking {
        currencyCacheDataSource.cacheNotEmpty()
        favoriteCacheDataSource.hasData()
        dashBoardItemsDataSource.hasException(error = UnknownHostException())
        val expected: DashboardResult =
            DashboardResult.Error(UnknownHostException().javaClass.name.toString())
        val actual: DashboardResult = dashboardRepository.dashboardItems()
        assertEquals(expected, actual)
    }

    @Test
    fun testRemovePair() = runBlocking {
        currencyCacheDataSource.cacheNotEmpty()
        loadCurrencyCloudDataSource.successLoadResult()
        favoriteCacheDataSource.hasData()
        val expected = DashboardResult.Success(listOf(DashBoardItem.Base("C", "D", 1.0)))
        val actual = dashboardRepository.removeItem(from = "A", to = "B")
        assertEquals(expected, actual)
//        dashboardRepository.removeItem(from = "C", to = "D")
//        favoriteCacheDataSource.checkAfterRemove(
//             CurrencyPair("A", "B", 1.0, 1))
    }
}

private class FakeHandleError : HandleError {
    override fun handle(error: Exception): String {
        return error.javaClass.name.toString()
    }
}

private class FakeDashBoardItemsDataSource : DashBoardItemsDataSource {

    //    var actualDashBoardItems: List<DashBoardItem> =
//        listOf(DashBoardItem.Base("A", "B", 1.0), DashBoardItem.Base("C", "D", 1.0))
    private var isSuccess: Boolean = true
    private lateinit var exception: Exception

    override suspend fun dashboardItems(favoritePairs: List<CurrencyPair>): List<DashBoardItem> {
        if (isSuccess) {
            return favoritePairs.map { DashBoardItem.Base(it.fromCurrency, it.toCurrency, it.rate) }
        } else {
            throw exception
        }
    }

    fun hasException(error: Exception) {
        isSuccess = false
        exception = error
    }

}

private class FakeLoadCurrencyCloudDataSource : LoadCurrencyCloudDataSource {

    private var isSuccessResult: Boolean = false
    private lateinit var exception: UnknownHostException

    override suspend fun loadCurrencies(): HashMap<String, String> {
        if (isSuccessResult) {
            return hashMapOf("A" to "A", "B" to "B")
        } else throw exception
    }

    fun successLoadResult() {
        isSuccessResult = true
    }

    fun errorLoadResult(exception: UnknownHostException) {
        this.exception = exception
    }

}

private class FakeCurrencyCacheDataSource : CurrencyCacheDataSource.Mutable {

    private var actualCurrencies: List<CurrencyCache> = emptyList()

    override suspend fun save(currencies: List<CurrencyCache>) {
        actualCurrencies = currencies
    }

    override suspend fun read(): List<CurrencyCache> {
        return actualCurrencies
    }

    fun cacheEmpty() {
        actualCurrencies = emptyList()
    }

    fun cacheNotEmpty() {
        actualCurrencies = listOf(CurrencyCache("A", "A"), CurrencyCache("B", "B"))
    }

}

