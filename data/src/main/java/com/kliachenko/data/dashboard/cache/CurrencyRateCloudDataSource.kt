package com.kliachenko.data.dashboard.cache

import com.kliachenko.data.dashboard.cloud.CurrencyPairCloud
import com.kliachenko.data.dashboard.cloud.CurrencyRateService

interface CurrencyRateCloudDataSource {

    suspend fun rate(fromCurrency: String, toCurrency: String): Double

    class Base(private val service: CurrencyRateService) : CurrencyRateCloudDataSource {

        override suspend fun rate(fromCurrency: String, toCurrency: String): Double {
            val currencyPair: CurrencyPairCloud =
                service.currencyValue(fromCurrency, toCurrency).execute().body()!!
            return currencyPair.rate(fromCurrency)
        }

    }
}