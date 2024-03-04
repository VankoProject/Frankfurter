package com.kliachenko.data.core

import android.content.Context
import androidx.room.Room
import com.kliachenko.data.loading.cache.CurrencyDataBase

interface ProvideCurrencyDataBase {

    fun dataBase(): CurrencyDataBase

    class Base(private val context: Context) : ProvideCurrencyDataBase {

        private val dataBase by lazy {
            Room.databaseBuilder(
                context,
                CurrencyDataBase::class.java,
                "currencyDataBase"
            ).build()
        }

        override fun dataBase(): CurrencyDataBase = dataBase
    }
}