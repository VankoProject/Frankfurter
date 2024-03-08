package com.kliachenko.frankfurter.core

import android.content.Context
import com.kliachenko.data.core.ProvideCurrencyDataBase
import com.kliachenko.data.core.ProvideResources
import com.kliachenko.data.core.ProvideRetrofit
import com.kliachenko.data.settings.BasePremiumUserStorage
import com.kliachenko.domain.settings.PremiumUserStorage
import com.kliachenko.presentation.core.Delimiter
import com.kliachenko.presentation.core.Navigation
import com.kliachenko.presentation.core.RunAsync

interface Core {

    fun provideNavigation(): Navigation

    fun provideRunAsync(): RunAsync

    fun provideResources(): ProvideResources

    fun provideCurrencyDataBase(): ProvideCurrencyDataBase

    fun provideRetrofit(): ProvideRetrofit

    fun provideDelimiter(): Delimiter

    fun providePremiumUserStorage(): PremiumUserStorage

    class Base(context: Context) : Core {

        private val navigation: Navigation by lazy { Navigation.Base() }
        private val provideResources by lazy { BaseProvideResources(context) }
        private val runAsync by lazy { RunAsync.Base() }
        private val provideCurrencyDataBase by lazy { ProvideCurrencyDataBase.Base(context) }
        private val provideRetrofit by lazy { ProvideRetrofit.Base() }
        private val delimiter = Delimiter.Base()
        private val premiumUserStorage = BasePremiumUserStorage(context)

        override fun provideNavigation() = navigation

        override fun provideRunAsync() = runAsync

        override fun provideResources() = provideResources

        override fun provideCurrencyDataBase() = provideCurrencyDataBase

        override fun provideRetrofit(): ProvideRetrofit = provideRetrofit

        override fun provideDelimiter() = delimiter

        override fun providePremiumUserStorage() = premiumUserStorage

    }
}