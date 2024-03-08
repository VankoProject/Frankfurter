package com.kliachenko.domain.settings

interface PremiumUserStorage {

    fun isPremium(): Boolean

    fun savePremiumUser()

}