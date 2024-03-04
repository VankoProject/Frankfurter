package com.kliachenko.data.dashboard

import com.kliachenko.data.core.HandleError
import com.kliachenko.data.dashboard.cache.FavoritePairCacheDataSource
import com.kliachenko.domain.dashboard.DashBoardItem
import com.kliachenko.domain.dashboard.DashboardRepository
import com.kliachenko.domain.dashboard.DashboardResult

class BaseDashboardRepository(
    private val favoriteCacheDataSource: FavoritePairCacheDataSource.Read,
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
}