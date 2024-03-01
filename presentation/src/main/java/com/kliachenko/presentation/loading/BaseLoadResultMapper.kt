package com.kliachenko.presentation.loading

import com.kliachenko.domain.load.LoadCurrenciesResult
import com.kliachenko.presentation.core.Clear
import com.kliachenko.presentation.core.Screen
import com.kliachenko.presentation.core.UpdateUi
import com.kliachenko.presentation.dashboard.DashBoardScreen

class BaseLoadResultMapper(
    private val observable: UpdateUi<LoadUiState>,
    private val navigation: UpdateUi<Screen>,
    private val clearViewModel: Clear,
) : LoadCurrenciesResult.Mapper {

    override fun mapSuccess() {
        navigation.updateUi(DashBoardScreen)
        clearViewModel.clear(LoadViewModel::class.java)
    }

    override fun mapError(message: String) {
        observable.updateUi(LoadUiState.Error(message))
    }
}