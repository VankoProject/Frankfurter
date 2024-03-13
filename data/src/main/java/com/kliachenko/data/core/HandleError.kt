package com.kliachenko.data.core

import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Singleton

interface HandleError {

    fun handle(error: Exception): String

    @Singleton
    class Base @Inject constructor(private val provideResources: ProvideResources) : HandleError {

        override fun handle(error: Exception) = with(provideResources) {
            if (error is UnknownHostException)
                noInternetConnectionMessage()
            else
                serviceUnavailableMessage()
        }
    }
}