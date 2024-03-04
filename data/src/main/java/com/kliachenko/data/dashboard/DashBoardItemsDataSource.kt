package com.kliachenko.data.dashboard

import com.kliachenko.data.dashboard.cache.CurrencyPair
import com.kliachenko.data.dashboard.cache.CurrentTimeInMillis
import com.kliachenko.data.dashboard.cache.FavoritePairCacheDataSource
import com.kliachenko.data.dashboard.cloud.CurrencyRateCloudDataSource
import com.kliachenko.domain.dashboard.DashBoardItem

interface DashBoardItemsDataSource {

    suspend fun dashboardItems(favoritePairs: List<CurrencyPair>): List<DashBoardItem>

    class Base(
        private val currentTimeInMillis: CurrentTimeInMillis,
        private val updatedRate: UpdatedRate,
    ) : DashBoardItemsDataSource {

        override suspend fun dashboardItems(favoritePairs: List<CurrencyPair>) =
            favoritePairs.map { currentPair ->
                DashBoardItem.Base(
                    currentPair.fromCurrency,
                    currentPair.toCurrency,
                    rate = if (currentPair.isInvalid(currentTimeInMillis))
                        updatedRate.updatedRate(currentPair)
                    else
                        currentPair.rate
                )
            }
    }
}

interface UpdatedRate {

    suspend fun updatedRate(currentPair: CurrencyPair): Double

    class Base(
        private val cacheDataSource: FavoritePairCacheDataSource.Save,
        private val currentTimeInMillis: CurrentTimeInMillis,
        private val rateCloudDataSource: CurrencyRateCloudDataSource,
    ) : UpdatedRate {

        override suspend fun updatedRate(currentPair: CurrencyPair): Double {
            val updateRate = rateCloudDataSource.rate(
                currentPair.fromCurrency,
                currentPair.toCurrency
            )
            val updatePair = CurrencyPair(
                currentPair.fromCurrency,
                currentPair.toCurrency,
                updateRate,
                currentTimeInMillis.currentTime()
            )
            cacheDataSource.saveFavoritePair(updatePair)
            return updateRate
        }
    }
}