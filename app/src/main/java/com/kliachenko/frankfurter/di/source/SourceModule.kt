package com.kliachenko.frankfurter.di.source

import com.kliachenko.data.dashboard.cache.currencyCache.CurrencyCacheDataSource
import com.kliachenko.data.dashboard.cache.currencyPair.FavoritePairCacheDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SourceModule {

    @Binds
    @Singleton
    abstract fun bindFavoritePairCacheDataSource(
        favoritePairCacheDataSource: FavoritePairCacheDataSource.Base
    ): FavoritePairCacheDataSource.Mutable

    @Binds
    @Singleton
    abstract fun bindFavoritePairCacheDataSourceRead(
        favoritePairCacheDataSource: FavoritePairCacheDataSource.Mutable
    ): FavoritePairCacheDataSource.Read

    @Binds
    @Singleton
    abstract fun bindFavoritePairCacheDataSourceSave(
        favoritePairCacheDataSource: FavoritePairCacheDataSource.Mutable
    ): FavoritePairCacheDataSource.Save

    @Binds
    @Singleton
    abstract fun bindCurrencyCacheDataSource(
        currencyCacheDataSource: CurrencyCacheDataSource.Base
    ): CurrencyCacheDataSource.Mutable

    @Binds
    @Singleton
    abstract fun bindCurrencyCacheDataSourceRead(
        currencyCacheDataSource: CurrencyCacheDataSource.Mutable
    ): CurrencyCacheDataSource.Read

    @Binds
    @Singleton
    abstract fun bindCurrencyCacheDataSourceSave(
        currencyCacheDataSource: CurrencyCacheDataSource.Mutable
    ): CurrencyCacheDataSource.Save

}