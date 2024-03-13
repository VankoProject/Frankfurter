package com.kliachenko.frankfurter.di.source

import android.content.Context
import androidx.room.Room
import com.kliachenko.data.dashboard.cache.currencyCache.CurrencyDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class CacheModule {

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): CurrencyDataBase =
        Room.databaseBuilder(
            context = context,
            klass = CurrencyDataBase::class.java,
            name = DB_NAME
        ).build()

    companion object {
        private const val DB_NAME = "currencyDataBase"
    }

    @Provides
    fun provideCurrencyDao(dataBase: CurrencyDataBase) = dataBase.currencyDao()

    @Provides
    fun provideCurrencyPairDao(dataBase: CurrencyDataBase) = dataBase.currencyPairDao()

}