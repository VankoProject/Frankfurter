package com.kliachenko.frankfurter.core

import android.content.Context
import com.kliachenko.data.loading.cache.CurrencyCacheDataSource
import com.kliachenko.presentation.core.Navigation
import com.kliachenko.presentation.core.RunAsync

interface Core {

    fun provideNavigation(): Navigation

    fun provideRunAsync(): RunAsync

    fun provideCacheDataSource(): CurrencyCacheDataSource.Mutable

    class Base(private val context: Context) : Core {

        private val currencyCacheDataSource: CurrencyCacheDataSource.Mutable by lazy {
            CurrencyCacheDataSource.Base(context = context)
        }
        private val navigation: Navigation by lazy {
            Navigation.Base()
        }
        private val runAsync: RunAsync by lazy {
            RunAsync.Base()
        }

        override fun provideNavigation() = navigation

        override fun provideRunAsync() = runAsync

        override fun provideCacheDataSource() = currencyCacheDataSource
    }
}
