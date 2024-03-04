package com.kliachenko.data.core

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface ProvideRetrofit {

    fun retrofit(): Retrofit

    class Base : ProvideRetrofit {
        override fun retrofit(): Retrofit {
            return Retrofit.Builder()
                .baseUrl("https://www.frankfurter.app/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(
                    OkHttpClient.Builder()
                        .addInterceptor(HttpLoggingInterceptor().apply {
                            setLevel(HttpLoggingInterceptor.Level.BODY)
                        }).build()
                )
                .build()
        }

    }
}