package com.kliachenko.data.dashboard

import com.kliachenko.data.core.HandleError
import com.kliachenko.data.dashboard.cache.CurrencyPair
import com.kliachenko.domain.dashboard.DashBoardItem
import com.kliachenko.domain.dashboard.DashboardRepository
import com.kliachenko.domain.dashboard.DashboardResult
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.net.UnknownHostException

class BaseDashboardRepositoryTest {

    private lateinit var cacheDataSource: FakeFavoritePairCacheDataSource
    private lateinit var dashBoardItemsDataSource: FakeDashBoardItemsDataSource
    private lateinit var handleError: FakeHandleError
    private lateinit var dashboardRepository: DashboardRepository

    @Before
    fun setup() {
        cacheDataSource = FakeFavoritePairCacheDataSource()
        dashBoardItemsDataSource = FakeDashBoardItemsDataSource()
        handleError = FakeHandleError()
        dashboardRepository = BaseDashboardRepository(
            cacheDataSource,
            dashBoardItemsDataSource,
            handleError
        )
    }

    @Test
    fun testEmpty() = runBlocking {
        cacheDataSource.emptyList()
        val expected: DashboardResult = DashboardResult.Empty
        val actual: DashboardResult = dashboardRepository.dashboardItems()
        assertEquals(expected, actual)
    }

    @Test
    fun testSuccess() = runBlocking {
        cacheDataSource.hasData()
        val expected: DashboardResult = DashboardResult.Success(
            listOf(DashBoardItem.Base("A", "B", 1.0))
        )
        val actual: DashboardResult = dashboardRepository.dashboardItems()
        assertEquals(expected, actual)
    }

    @Test
    fun testError() = runBlocking {
        cacheDataSource.hasData()
        dashBoardItemsDataSource.hasException(error = UnknownHostException())
        val expected: DashboardResult =
            DashboardResult.Error(UnknownHostException().javaClass.name.toString())
        val actual: DashboardResult = dashboardRepository.dashboardItems()
        assertEquals(expected, actual)

    }
}

private class FakeHandleError : HandleError {
    override fun handle(error: Exception): String {
        return error.javaClass.name.toString()
    }

}

private class FakeDashBoardItemsDataSource : DashBoardItemsDataSource {

    var actualDashBoardItems: List<DashBoardItem> =
        listOf(DashBoardItem.Base("A", "B", 1.0))
    private var isSuccess: Boolean = true
    private lateinit var exception: Exception

    override suspend fun dashboardItems(favoritePairs: List<CurrencyPair>): List<DashBoardItem> {
        if (isSuccess) {
            return actualDashBoardItems
        } else {
            throw exception
        }
    }

    fun hasException(error: Exception) {
        isSuccess = false
        exception = error
    }

}

