package com.kliachenko.presentation.loading

import com.kliachenko.domain.LoadResult
import com.kliachenko.domain.MainRepository
import com.kliachenko.presentation.core.BaseViewModel
import com.kliachenko.presentation.core.RunAsync
import com.kliachenko.presentation.core.UpdateUi

class LoadViewModel(
    private val observable: LoadUiObservable,
    private val repository: MainRepository,
    runAsync: RunAsync,
    private val mapper: LoadResult.Mapper,
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