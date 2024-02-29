package com.kliachenko.data.dashboard.cloud

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyRateService {

    @GET("latest")
    fun currencyValue(
        @Query("from") fromCurrency: String,
        @Query("to") toCurrency: String,
    ): Call<CurrencyPairCloud>
}

data class CurrencyPairCloud(
    @SerializedName ("rates")
    val map: HashMap<String, Double>,
) {
    fun rate(currency: String): Double {
        return map[currency]!!
    }
}
