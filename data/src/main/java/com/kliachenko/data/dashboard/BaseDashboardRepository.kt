package com.kliachenko.data.dashboard

import com.kliachenko.data.core.HandleError
import com.kliachenko.data.dashboard.cache.currencyCache.CurrencyCache
import com.kliachenko.data.dashboard.cache.currencyCache.CurrencyCacheDataSource
import com.kliachenko.data.dashboard.cache.currencyPair.CurrencyPairCache
import com.kliachenko.data.dashboard.cache.currencyPair.FavoritePairCacheDataSource
import com.kliachenko.data.dashboard.cloud.currencyLoad.LoadCurrencyCloudDataSource
import com.kliachenko.domain.dashboard.DashBoardItem
import com.kliachenko.domain.dashboard.DashboardRepository
import com.kliachenko.domain.dashboard.DashboardResult
import javax.inject.Inject

class BaseDashboardRepository @Inject constructor(
    private val loadCurrencyCloudDataSource: LoadCurrencyCloudDataSource,
    private val currencyCacheDataSource: CurrencyCacheDataSource.Mutable,
    private val favoriteCacheDataSource: FavoritePairCacheDataSource.Mutable,
    private val dashBoardItemsDataSource: DashBoardItemsDataSource,
    private val handleError: HandleError,
) : DashboardRepository {

    override suspend fun dashboardItems(): DashboardResult = try {
        if (currencyCacheDataSource.read().isEmpty()) {
            val result = loadCurrencyCloudDataSource.loadCurrencies().map {
                CurrencyCache(it.key, it.value)
            }
            currencyCacheDataSource.save(result)
        }
        val favoritePairs = favoriteCacheDataSource.favoriteCurrencyPairs()
        if (favoritePairs.isEmpty()) {
            DashboardResult.Empty
        } else {
            val updatePair: List<DashBoardItem> =
                dashBoardItemsDataSource.dashboardItems(favoritePairs)
            DashboardResult.Success(updatePair)
        }
    } catch (e: Exception) {
        DashboardResult.Error(handleError.handle(e))
    }

    override suspend fun removeItem(from: String, to: String): DashboardResult {
        favoriteCacheDataSource.removeCurrencyPair(
            CurrencyPairCache(
                fromCurrency = from,
                toCurrency = to
            )
        )
        return dashboardItems()
    }
}