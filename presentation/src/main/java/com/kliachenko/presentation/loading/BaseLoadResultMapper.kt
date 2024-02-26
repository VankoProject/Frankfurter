package com.kliachenko.presentation.loading

import com.kliachenko.domain.CurrencyModel
import com.kliachenko.domain.LoadResult
import com.kliachenko.presentation.core.UpdateUi

class BaseLoadResultMapper(
    private val observable: UpdateUi<LoadUiState>,
) : LoadResult.Mapper {

    override fun mapSuccess(data: List<CurrencyModel>) {
        observable.updateUi(LoadUiState.Success)
    }

    override fun mapError(message: String) {
        observable.updateUi(LoadUiState.Error(message))
    }
}