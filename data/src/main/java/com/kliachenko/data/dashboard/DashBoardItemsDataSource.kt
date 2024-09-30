package com.kliachenko.data.dashboard

import com.kliachenko.data.dashboard.cache.currencyPair.CurrencyPair
import com.kliachenko.data.dashboard.cache.currencyPair.CurrencyPairCache
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
        private val dispatcher: CoroutineDispatcher,
        private val mapper: CurrencyPair.Mapper<DashBoardItem>,
    ) : DashBoardItemsDataSource {

        override suspend fun dashboardItems(favoritePairs: List<CurrencyPair>) =
            withContext(dispatcher) {
                favoritePairs.map { currentPair ->
                    async {
                        currentPair.map(mapper)
                    }
                }.awaitAll()
            }
    }
}

class DashboardItemMapper @Inject constructor(
    private val updatedRate: UpdatedRate,
    private val isInvalidCacheTime: IsInvalidCacheRateAndTime,
) : CurrencyPair.Mapper<DashBoardItem> {
    override suspend fun map(from: String, to: String, rate: Double, time: Long): DashBoardItem {
        return DashBoardItem.Base(
            fromCurrency = from,
            toCurrency = to,
            rate = if (isInvalidCacheTime.isInvalid(rate, time))
                updatedRate.updatedRate(from, to)
            else
                rate
        )
    }
}

interface IsInvalidCacheRateAndTime {

    fun isInvalid(rate: Double, time: Long): Boolean

    class Base @Inject constructor(private val currentTimeInMillis: CurrentTimeInMillis) :
        IsInvalidCacheRateAndTime {
        override fun isInvalid(rate: Double, time: Long): Boolean {
            return currentTimeInMillis.currentTime() - time > 24 * 3600 * 1000 || rate == 0.0
        }

    }
}

interface UpdatedRate {

    suspend fun updatedRate(from: String, to: String): Double

    class Base @Inject constructor(
        private val cacheDataSource: FavoritePairCacheDataSource.Save,
        private val currentTimeInMillis: CurrentTimeInMillis,
        private val rateCloudDataSource: CurrencyRateCloudDataSource,
    ) : UpdatedRate {

        override suspend fun updatedRate(from: String, to: String): Double {
            val updateRate = rateCloudDataSource.rate(
                fromCurrency = from,
                toCurrency = to
            )
            val updatePair = CurrencyPairCache(
                fromCurrency = from,
                toCurrency = to,
                updateRate,
                currentTimeInMillis.currentTime()
            )
            cacheDataSource.saveFavoritePair(updatePair)
            return updateRate
        }
    }
}