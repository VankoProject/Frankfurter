package com.kliachenko.frankfurter.di.settings

import com.kliachenko.frankfurter.core.ProvideInstance
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SettingsModuleImpl {

    @Provides
    @Singleton
    fun provideMaxCount(provideInstance: ProvideInstance): Int {
        return provideInstance.provideFreeCountPair()
    }

}