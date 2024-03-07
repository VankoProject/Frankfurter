package com.kliachenko.data.dashboard.cloud

interface CurrencyRateCloudDataSource {

    suspend fun rate(fromCurrency: String, toCurrency: String): Double

    class Base(private val service: CurrencyRateService) : CurrencyRateCloudDataSource {

        override suspend fun rate(fromCurrency: String, toCurrency: String): Double {
            val currencyPair: CurrencyPairCloud =
                service.currencyValue(fromCurrency, toCurrency).execute().body()!!
            return currencyPair.rate(toCurrency)
        }
    }

    class Fake : CurrencyRateCloudDataSource {

        override suspend fun rate(fromCurrency: String, toCurrency: String): Double {
            return 15.5
        }

    }
}