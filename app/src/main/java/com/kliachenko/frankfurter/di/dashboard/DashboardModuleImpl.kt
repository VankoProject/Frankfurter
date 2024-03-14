package com.kliachenko.frankfurter.di.dashboard

import com.kliachenko.data.dashboard.UpdatedRate
import com.kliachenko.data.dashboard.cache.currencyPair.CurrentTimeInMillis
import com.kliachenko.data.dashboard.cache.currencyPair.FavoritePairCacheDataSource
import com.kliachenko.data.dashboard.cloud.currencyRate.CurrencyRateCloudDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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

}