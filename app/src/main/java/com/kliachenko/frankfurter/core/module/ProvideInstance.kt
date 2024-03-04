package com.kliachenko.frankfurter.core.module

import com.kliachenko.data.core.HandleError
import com.kliachenko.data.core.ProvideResources
import com.kliachenko.data.dashboard.BaseDashboardRepository
import com.kliachenko.data.dashboard.DashBoardItemsDataSource
import com.kliachenko.data.dashboard.cache.FavoritePairCacheDataSource
import com.kliachenko.data.loading.BaseLoadCurrencyRepository
import com.kliachenko.data.loading.cache.CurrencyCacheDataSource
import com.kliachenko.data.loading.cloud.LoadCurrencyCloudDataSource
import com.kliachenko.data.settings.BaseSettingsRepository
import com.kliachenko.domain.dashboard.DashboardRepository
import com.kliachenko.domain.load.LoadCurrenciesRepository
import com.kliachenko.domain.settings.SettingsRepository

interface ProvideInstance : ProvideLoadRepository, ProvideSettingsRepository,
    ProvideDashBoardRepository {

    class Base : ProvideInstance {
        override fun provideLoadRepository(
            cacheDataSource: CurrencyCacheDataSource.Mutable,
            cloudDataSource: LoadCurrencyCloudDataSource,
            provideResources: ProvideResources,
        ) = BaseLoadCurrencyRepository(
            loadCurrencyCloudDataSource = cloudDataSource,
            currencyCacheDataSource = cacheDataSource,
            provideResources = provideResources
        )

        override fun provideDashBoardRepository(
            cacheDataSource: FavoritePairCacheDataSource.Read,
            handleError: HandleError,
            dashBoardItemsDataSource: DashBoardItemsDataSource,
        ): DashboardRepository {
            return BaseDashboardRepository(
                favoriteCacheDataSource = cacheDataSource,
                handleError = handleError,
                dashBoardItemsDataSource = dashBoardItemsDataSource
            )
        }

        override fun provideSettingsRepository(
            cacheDataSource: CurrencyCacheDataSource.Mutable,
            favoritePairCacheDataSource: FavoritePairCacheDataSource.Mutable,
        ): SettingsRepository {
            return BaseSettingsRepository(
                favoritePairCacheDataSource = favoritePairCacheDataSource,
                currencyCacheDataSource = cacheDataSource
            )
        }
    }

}

interface ProvideLoadRepository {
    fun provideLoadRepository(
        cacheDataSource: CurrencyCacheDataSource.Mutable,
        cloudDataSource: LoadCurrencyCloudDataSource,
        provideResources: ProvideResources,
    ): LoadCurrenciesRepository
}


interface ProvideDashBoardRepository {
    fun provideDashBoardRepository(
        cacheDataSource: FavoritePairCacheDataSource.Read,
        handleError: HandleError,
        dashBoardItemsDataSource: DashBoardItemsDataSource,
    ): DashboardRepository
}

interface ProvideSettingsRepository {
    fun provideSettingsRepository(
        cacheDataSource: CurrencyCacheDataSource.Mutable,
        favoritePairCacheDataSource: FavoritePairCacheDataSource.Mutable,
    ): SettingsRepository
}
