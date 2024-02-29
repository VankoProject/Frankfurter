package com.kliachenko.frankfurter.core

import android.content.Context
import com.kliachenko.data.core.ProvideResources
import com.kliachenko.data.loading.cache.ProvideCurrencyDataBase
import com.kliachenko.presentation.core.Navigation
import com.kliachenko.presentation.core.RunAsync

interface Core {

    fun provideNavigation(): Navigation

    fun provideRunAsync(): RunAsync

    fun provideResources(): ProvideResources

    fun provideCurrencyDataBase(): ProvideCurrencyDataBase

    class Base(context: Context) : Core {

        private val navigation: Navigation by lazy { Navigation.Base() }
        private val provideResources by lazy { BaseProvideResources(context) }
        private val runAsync by lazy { RunAsync.Base() }
        private val provideCurrencyDataBase by lazy { ProvideCurrencyDataBase.Base(context) }

        override fun provideNavigation() = navigation

        override fun provideRunAsync() = runAsync

        override fun provideResources() = provideResources

        override fun provideCurrencyDataBase() = provideCurrencyDataBase

    }
}