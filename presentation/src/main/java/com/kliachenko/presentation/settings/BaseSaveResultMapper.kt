package com.kliachenko.presentation.settings

import com.kliachenko.domain.settings.SaveResult
import com.kliachenko.presentation.core.Clear
import com.kliachenko.presentation.core.Navigation
import com.kliachenko.presentation.dashboard.DashBoardScreen
import com.kliachenko.presentation.subscritpion.SubscriptionScreen

class BaseSaveResultMapper(
    private val navigation: Navigation,
    private val clear: Clear,
) : SaveResult.Mapper {
    override fun mapSuccessSave() {
        navigation.updateUi(DashBoardScreen)
        clear.clear(SettingsViewModel::class.java)
    }

    override fun mapRequirePremium() {
        navigation.updateUi(SubscriptionScreen)
        clear.clear(SettingsViewModel::class.java)
    }
}