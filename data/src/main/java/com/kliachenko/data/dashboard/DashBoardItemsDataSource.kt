package com.kliachenko.data.dashboard

import com.kliachenko.data.dashboard.cache.currencyPair.CurrencyPair
import com.kliachenko.data.dashboard.cache.currencyPair.CurrentTimeInMillis
import com.kliachenko.data.dashboard.cache.currencyPair.FavoritePairCacheDataSource
import com.kliachenko.data.dashboard.cloud.currencyRate.CurrencyRateCloudDataSource
import com.kliachenko.domain.dashboard.DashBoardItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

interface DashBoardItemsDataSource {

    suspend fun dashboardItems(favoritePairs: List<CurrencyPair>): List<DashBoardItem>

    @Singleton
    class Base @Inject constructor(
        private val currentTimeInMillis: CurrentTimeInMillis,
        private val updatedRate: UpdatedRate,
        private val dispatcher: CoroutineDispatcher,
    ) : DashBoardItemsDataSource {

        override suspend fun dashboardItems(favoritePairs: List<CurrencyPair>) =
            withContext(dispatcher) {
                favoritePairs.map { currentPair ->
                    async {
                        DashBoardItem.Base(
                            currentPair.fromCurrency,
                            currentPair.toCurrency,
                            rate = if (currentPair.isInvalid(currentTimeInMillis))
                                updatedRate.updatedRate(currentPair)
                            else
                                currentPair.rate
                        )
                    }
                }.awaitAll()
            }
    }
}

interface UpdatedRate {

    suspend fun updatedRate(currentPair: CurrencyPair): Double

    class Base @Inject constructor(
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