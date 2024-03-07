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

        private var firstTime: Boolean = true

        override suspend fun rate(fromCurrency: String, toCurrency: String): Double {
            if (firstTime) {
                firstTime = false
                throw IllegalStateException()
            }
            return 15.5
        }

    }
}