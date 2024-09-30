package com.kliachenko.data.core

interface ProvideResources {

    fun noInternetConnectionMessage(): String

    fun serviceUnavailableMessage() : String
}