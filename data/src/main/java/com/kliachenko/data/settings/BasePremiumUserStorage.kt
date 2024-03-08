package com.kliachenko.data.settings

import android.content.Context
import com.kliachenko.domain.settings.PremiumUserStorage

class BasePremiumUserStorage(context: Context) : PremiumUserStorage {

    private val sharedPreferences =
        context.getSharedPreferences("premiumUserStorage", Context.MODE_PRIVATE)

    override fun isPremium(): Boolean {
        return sharedPreferences.getBoolean(PREMIUM_KEY, false)
    }

    override fun savePremiumUser() {
        sharedPreferences.edit().putBoolean(PREMIUM_KEY, true).apply()
    }

    companion object {
        private const val PREMIUM_KEY = "premium_key"
    }
}