package com.kliachenko.frankfurter.di.core

import com.kliachenko.data.core.HandleError
import com.kliachenko.data.core.ProvideResources
import com.kliachenko.data.dashboard.cache.CurrentTimeInMillis
import com.kliachenko.frankfurter.core.BaseProvideResources
import com.kliachenko.frankfurter.core.ProvideInstance
import com.kliachenko.presentation.core.Navigation
import com.kliachenko.presentation.core.RunAsync
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CoreModule {

    @Binds
    @Singleton
    abstract fun bindProvideInstance(provideInstance: ProvideInstance.Mock): ProvideInstance

    @Binds
    @Singleton
    abstract fun bindProvideResources(provideResources: BaseProvideResources): ProvideResources

    @Binds
    @Singleton
    abstract fun bindNavigation(navigation: Navigation.Base): Navigation

    @Binds
    @Singleton
    abstract fun bindHandleError(handleError: HandleError.Base): HandleError

    @Binds
    @Singleton
    abstract fun bindCurrentTimeMillis(currentTimeInMillis: CurrentTimeInMillis.Base): CurrentTimeInMillis

    @Binds
    @Singleton
    abstract fun bindRunAsync(runAsync: RunAsync.Base): RunAsync

}