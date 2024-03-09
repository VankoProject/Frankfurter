package com.kliachenko.presentation.subscritpion

import com.kliachenko.domain.settings.PremiumUserStorage
import com.kliachenko.presentation.core.Clear
import com.kliachenko.presentation.core.CustomViewModel
import com.kliachenko.presentation.core.Navigation
import com.kliachenko.presentation.core.Screen

class SubscriptionViewModel(
    private val navigation: Navigation,
    private val premiumUserStorage: PremiumUserStorage.Save,
    private val clear: Clear,
) : CustomViewModel {

    fun buyPremium() {
        premiumUserStorage.savePremiumUser()
        goSettingScreen()
    }

    fun goSettingScreen() {
        navigation.updateUi(Screen.Pop)
        clear.clear(SubscriptionViewModel::class.java)
    }
}