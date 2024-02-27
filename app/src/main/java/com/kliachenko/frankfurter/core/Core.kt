package com.kliachenko.frankfurter.core

import android.content.Context
import com.kliachenko.data.ProvideResources
import com.kliachenko.data.loading.cache.ProvideCurrencyDataBase
import com.kliachenko.presentation.core.Navigation
import com.kliachenko.presentation.core.RunAsync

interface Core {

    fun provideNavigation(): Navigation

    fun provideRunAsync(): RunAsync

    fun provideResources(): ProvideResources

    fun provideCurrencyDataBase(): ProvideCurrencyDataBase

    class Base(context: Context) : Core {

        private val navigation: Navigation by lazy {
            Navigation.Base()
        }
        private val runAsync: RunAsync by lazy {
            RunAsync.Base()
        }

        private val provideCurrencyDataBase = ProvideCurrencyDataBase.Base(context)

        private val provideResources = BaseProvideResources(context)

        override fun provideNavigation() = navigation

        override fun provideRunAsync() = runAsync

        override fun provideResources() = provideResources

        override fun provideCurrencyDataBase() = provideCurrencyDataBase

    }
}
