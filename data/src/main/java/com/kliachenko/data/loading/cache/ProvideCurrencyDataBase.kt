package com.kliachenko.data.loading.cache

import android.content.Context
import androidx.room.Room

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