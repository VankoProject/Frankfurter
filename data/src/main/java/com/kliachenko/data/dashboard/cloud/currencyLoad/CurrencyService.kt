package com.kliachenko.data.dashboard.cloud.currencyLoad

import retrofit2.Call
import retrofit2.http.GET

interface CurrencyService {

    @GET("currencies")
    fun currencies(): Call<HashMap<String, String>>
}