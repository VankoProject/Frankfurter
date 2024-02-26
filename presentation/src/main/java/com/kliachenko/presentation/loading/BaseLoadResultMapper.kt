package com.kliachenko.presentation.loading

import com.kliachenko.domain.LoadResult
import com.kliachenko.presentation.core.Clear
import com.kliachenko.presentation.core.Screen
import com.kliachenko.presentation.core.UpdateUi
import com.kliachenko.presentation.dashboard.DashBoardScreen

class BaseLoadResultMapper(
    private val observable: UpdateUi<LoadUiState>,
    private val navigation: UpdateUi<Screen>,
    private val clear: Clear,
) : LoadResult.Mapper {

    override fun mapSuccess() {
        navigation.updateUi(DashBoardScreen.Initial)
        clear.clear(LoadViewModel::class.java)
    }

    override fun mapError(message: String) {
        observable.updateUi(LoadUiState.Error(message))
    }
}