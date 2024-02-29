package com.kliachenko.data.dashboard.cache

interface CurrentTimeInMillis {

    fun currentTime(): Long

    class Base: CurrentTimeInMillis {
        override fun currentTime(): Long {
            return System.currentTimeMillis()
        }
    }

}