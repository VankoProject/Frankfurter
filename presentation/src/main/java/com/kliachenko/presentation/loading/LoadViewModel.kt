package com.kliachenko.presentation.loading

import com.kliachenko.domain.load.LoadCurrenciesRepository
import com.kliachenko.domain.load.LoadCurrenciesResult
import com.kliachenko.presentation.core.BaseViewModel
import com.kliachenko.presentation.core.RunAsync

class LoadViewModel(
    private val observable: LoadUiObservable,
    private val repository: LoadCurrenciesRepository,
    runAsync: RunAsync,
    private val mapper: LoadCurrenciesResult.Mapper,
) : BaseViewModel<LoadUiState>(observable, runAsync) {

    fun init(firstRun: Boolean) {
        if (firstRun)
            load()
    }

    fun load() {
        observable.updateUi(LoadUiState.Progress)
        runAsync({
            repository.loadCurrencies()
        }) {
            it.map(mapper)
        }
    }

}