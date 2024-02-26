package com.kliachenko.data.loading.cloud

import retrofit2.Call
import retrofit2.http.GET

interface CurrencyService {

    @GET("currencies")
    fun currencies(): Call<HashMap<String, String>>
}