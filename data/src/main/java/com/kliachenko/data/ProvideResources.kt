package com.kliachenko.data

interface ProvideResources {

    fun noInternetConnectionMessage(): String

    fun serviceUnavailableMessage() : String
}