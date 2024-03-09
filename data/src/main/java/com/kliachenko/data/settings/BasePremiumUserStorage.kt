package com.kliachenko.data.settings

import android.content.SharedPreferences
import com.kliachenko.domain.settings.PremiumUserStorage

class BasePremiumUserStorage(
    private val sharedPreferences: SharedPreferences,
    private val key: String = "premium_key",
) : PremiumUserStorage.Mutable {

    override fun isPremium(): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    override fun savePremiumUser() {
        sharedPreferences.edit().putBoolean(key, true).apply()
    }

}