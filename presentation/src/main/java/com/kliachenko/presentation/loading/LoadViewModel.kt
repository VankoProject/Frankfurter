package com.kliachenko.presentation.loading

import com.kliachenko.domain.load.LoadCurrenciesRepository
import com.kliachenko.domain.load.LoadCurrenciesResult
import com.kliachenko.presentation.core.BaseViewModel
import com.kliachenko.presentation.core.RunAsync
import com.kliachenko.presentation.core.UpdateUi

class LoadViewModel(
    private val observable: LoadUiObservable,
    private val repository: LoadCurrenciesRepository,
    runAsync: RunAsync,
    private val mapper: LoadCurrenciesResult.Mapper,
) : BaseViewModel(runAsync) {

    fun init(firstRun: Boolean) {
        if(firstRun)
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

    fun startGettingUpdates(observer: UpdateUi<LoadUiState>) {
        observable.updateObserver(observer)
    }

    fun stopGettingUpdates() {
        observable.updateObserver(UpdateUi.Empty())
    }

}