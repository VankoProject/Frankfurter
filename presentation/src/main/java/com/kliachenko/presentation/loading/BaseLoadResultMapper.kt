package com.kliachenko.presentation.loading

import com.kliachenko.domain.load.LoadCurrenciesResult
import com.kliachenko.presentation.core.Navigation
import com.kliachenko.presentation.dashboard.DashBoardScreen
import javax.inject.Inject

class BaseLoadResultMapper @Inject constructor(
    private val observable: LoadUiObservable,
    private val navigation: Navigation,
) : LoadCurrenciesResult.Mapper {

    override fun mapSuccess() {
        navigation.updateUi(DashBoardScreen)
    }

    override fun mapError(message: String) {
        observable.updateUi(LoadUiState.Error(message))
    }
}