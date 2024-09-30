package com.kliachenko.frankfurter.di.dashboard

import com.kliachenko.data.dashboard.DashBoardItemsDataSource
import com.kliachenko.data.dashboard.DashboardItemMapper
import com.kliachenko.data.dashboard.IsInvalidCacheRateAndTime
import com.kliachenko.data.dashboard.UpdatedRate
import com.kliachenko.data.dashboard.cache.currencyPair.CurrencyPair
import com.kliachenko.data.dashboard.cache.currencyPair.CurrentTimeInMillis
import com.kliachenko.data.dashboard.cache.currencyPair.FavoritePairCacheDataSource
import com.kliachenko.data.dashboard.cloud.currencyRate.CurrencyRateCloudDataSource
import com.kliachenko.domain.dashboard.DashBoardItem
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DashboardModuleImpl {

    @Provides
    @Singleton
    fun provideDispatcherIO() = Dispatchers.IO

    @Provides
    @Singleton
    fun provideUpdateRate(
        cacheDataSource: FavoritePairCacheDataSource.Save,
        currentTimeInMillis: CurrentTimeInMillis,
        rateCloudDataSource: CurrencyRateCloudDataSource,
    ): UpdatedRate = UpdatedRate.Base(cacheDataSource, currentTimeInMillis, rateCloudDataSource)

    @Provides
    @Singleton
    fun provideIsInvalidCacheRateAndTime(
        currentTimeInMillis: CurrentTimeInMillis,
    ): IsInvalidCacheRateAndTime {
        return IsInvalidCacheRateAndTime.Base(currentTimeInMillis)
    }

    @Provides
    @Singleton
    fun provideDashboardIemMapper(
        updatedRate: UpdatedRate,
        isInvalidCacheRateAndTime: IsInvalidCacheRateAndTime,
    ): CurrencyPair.Mapper<DashBoardItem> {
        return DashboardItemMapper(updatedRate, isInvalidCacheRateAndTime)
    }


    @Provides
    @Singleton
    fun provideDashboardItemDataSource(
        dispatcher: CoroutineDispatcher,
        mapper: CurrencyPair.Mapper<DashBoardItem>,
    ): DashBoardItemsDataSource {
        return DashBoardItemsDataSource.Base(
            dispatcher, mapper
        )
    }
}