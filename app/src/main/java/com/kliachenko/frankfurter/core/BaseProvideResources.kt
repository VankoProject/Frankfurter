package com.kliachenko.frankfurter.core

import android.content.Context
import com.kliachenko.data.core.ProvideResources
import com.kliachenko.frankfurter.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BaseProvideResources @Inject constructor(@ApplicationContext private val context: Context): ProvideResources {

    override fun noInternetConnectionMessage() =
        context.resources.getString(R.string.no_internet_connection)

    override fun serviceUnavailableMessage() =
        context.resources.getString(R.string.service_unavailable)
}