package com.kliachenko.presentation.subscritpion

import androidx.lifecycle.ViewModel
import com.kliachenko.domain.settings.PremiumUserStorage
import com.kliachenko.presentation.core.Navigation
import com.kliachenko.presentation.core.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SubscriptionViewModel @Inject constructor(
    private val navigation: Navigation,
    private val premiumUserStorage: PremiumUserStorage.Save,
) : ViewModel() {

    fun buyPremium() {
        premiumUserStorage.savePremiumUser()
        goSettingScreen()
    }

    fun goSettingScreen() {
        navigation.updateUi(Screen.Pop)
    }
}