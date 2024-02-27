package com.kliachenko.frankfurter.core

import android.content.Context
import com.kliachenko.data.ProvideResources
import com.kliachenko.frankfurter.R

class BaseProvideResources(private val context: Context) : ProvideResources {

    override fun noInternetConnectionMessage(): String {
        return context.resources.getString(R.string.no_internet_connection)
    }

    override fun serviceUnavailableMessage(): String {
        return context.resources.getString(R.string.service_unavailable)
    }
}