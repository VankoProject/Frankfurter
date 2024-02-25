package com.kliachenko.presentation.loading

import com.kliachenko.presentation.core.BaseViewModel
import com.kliachenko.presentation.core.Clear
import com.kliachenko.presentation.core.Navigation
import com.kliachenko.presentation.core.RunAsync
import com.kliachenko.presentation.core.UpdateUi
import com.kliachenko.presentation.dashboard.DashBoardScreen

class LoadViewModel(
    private val observable: LoadUiObservable,
    private val repository: MainRepository,
    private val navigation: Navigation,
    private val clear: Clear,
    runAsync: RunAsync,
) : BaseViewModel(runAsync) {

    fun init() {
        observable.updateUi(LoadUiState.Progress)
        if(repository.categories().isEmpty) {
            load()
            navigation.updateUi(DashBoardScreen)
        } else {
            navigation.updateUi(DashBoardScreen)
        }
    }

    fun retry() {
        load()
    }

    private fun load() {
        observable.updateUi(LoadUiState.Progress)
        runAsync({
            repository.loadData()
        }) { loadResult ->
            loadResult.handle(observable)
        }
    }

    fun startGettingUpdates(observer: UpdateUi<LoadUiState>) {
        observable.updateObserver(observer)
    }

    fun stopGettingUpdates() {
        observable.updateUi(LoadUiState.Empty)
    }

}