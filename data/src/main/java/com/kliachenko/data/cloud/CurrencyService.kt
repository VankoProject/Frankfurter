package com.kliachenko.data.cloud

import retrofit2.Call
import retrofit2.http.GET

interface CurrencyService {

    //https://www.frankfurter.app/currencies
    @GET("currencies")
    fun currencies(): Call<HashMap<String, String>>
}