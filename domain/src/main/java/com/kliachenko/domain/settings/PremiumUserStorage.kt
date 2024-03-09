package com.kliachenko.domain.settings

interface PremiumUserStorage {

    interface Read {
        fun isPremium(): Boolean
    }

    interface Save {
        fun savePremiumUser()
    }

    interface Mutable: Save, Read

}