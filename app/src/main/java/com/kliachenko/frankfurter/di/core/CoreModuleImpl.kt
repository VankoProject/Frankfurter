package com.kliachenko.frankfurter.di.core

import android.content.Context
import android.content.SharedPreferences
import com.kliachenko.presentation.core.Delimiter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CoreModuleImpl {

    @Provides
    @Singleton
    fun provideDelimiter(): Delimiter = Delimiter.Base()

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val SP_NAME = "premiumUserStorage"
    }

}