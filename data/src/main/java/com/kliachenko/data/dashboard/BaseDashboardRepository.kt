package com.kliachenko.data.dashboard

import com.kliachenko.data.core.HandleError
import com.kliachenko.data.dashboard.cache.CurrencyPair
import com.kliachenko.data.dashboard.cache.FavoritePairCacheDataSource
import com.kliachenko.domain.dashboard.DashBoardItem
import com.kliachenko.domain.dashboard.DashboardRepository
import com.kliachenko.domain.dashboard.DashboardResult
import javax.inject.Inject

class BaseDashboardRepository @Inject constructor(
    private val favoriteCacheDataSource: FavoritePairCacheDataSource.Mutable,
    private val dashBoardItemsDataSource: DashBoardItemsDataSource,
    private val handleError: HandleError,
) : DashboardRepository {

    override suspend fun dashboardItems(): DashboardResult {
        val favoritePairs = favoriteCacheDataSource.favoriteCurrencyPairs()
        return if (favoritePairs.isEmpty()) {
            DashboardResult.Empty
        } else {
            try {
                val updatePair: List<DashBoardItem> =
                    dashBoardItemsDataSource.dashboardItems(favoritePairs)
                DashboardResult.Success(updatePair)
            } catch (e: Exception) {
                DashboardResult.Error(handleError.handle(e))
            }
        }
    }

    override suspend fun removeItem(from: String, to: String): DashboardResult {
        favoriteCacheDataSource.removeCurrencyPair(CurrencyPair(fromCurrency = from, toCurrency = to))
        return dashboardItems()
    }
}