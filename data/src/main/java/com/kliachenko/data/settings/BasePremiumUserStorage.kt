package com.kliachenko.data.settings

import android.content.SharedPreferences
import com.kliachenko.domain.settings.PremiumUserStorage
import javax.inject.Inject

class BasePremiumUserStorage @Inject constructor (
    private val sharedPreferences: SharedPreferences,
) : PremiumUserStorage.Mutable {

    override fun isPremium(): Boolean {
        return sharedPreferences.getBoolean(KEY, false)
    }

    override fun savePremiumUser() {
        sharedPreferences.edit().putBoolean(KEY, true).apply()
    }

    companion object {
        private const val KEY = "isUserPremium"
    }

}