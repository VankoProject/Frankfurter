package com.kliachenko.presentation.settings

import com.kliachenko.domain.settings.SaveResult
import com.kliachenko.presentation.core.Navigation
import com.kliachenko.presentation.dashboard.DashBoardScreen
import com.kliachenko.presentation.subscritpion.SubscriptionScreen
import javax.inject.Inject

class BaseSaveResultMapper @Inject constructor(
    private val navigation: Navigation,
) : SaveResult.Mapper {

    override fun mapSuccessSave() {
        navigation.updateUi(DashBoardScreen)
    }

    override fun mapRequirePremium() {
        navigation.updateUi(SubscriptionScreen)
    }
}