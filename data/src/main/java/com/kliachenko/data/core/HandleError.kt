package com.kliachenko.data.core

import java.net.UnknownHostException

interface HandleError {

    fun handle(error: Exception): String

    class Base(private val provideResources: ProvideResources) : HandleError {

        override fun handle(error: Exception)= with(provideResources) {
            if (error is UnknownHostException)
                noInternetConnectionMessage()
            else
                serviceUnavailableMessage()
        }
    }
}