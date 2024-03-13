package com.kliachenko.data.dashboard.cache.currencyPair

import javax.inject.Inject

interface CurrentTimeInMillis {

    fun currentTime(): Long

    class Base @Inject constructor() : CurrentTimeInMillis {
        override fun currentTime(): Long {
            return System.currentTimeMillis()
        }
    }

}