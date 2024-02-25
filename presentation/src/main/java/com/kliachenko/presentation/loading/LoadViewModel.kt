package com.kliachenko.presentation.loading

import com.kliachenko.domain.LoadResult
import com.kliachenko.domain.MainRepository
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
    private val mapper: LoadResult.Mapper = BaseLoadResultMapper(observable),
) : BaseViewModel(runAsync) {

    fun init() {
        observable.updateUi(LoadUiState.Progress)
        if (repository.loadCurrencies().isEmpty()) {
            load()
            navigation.updateUi(DashBoardScreen.Initial)
        } else {
            navigation.updateUi(DashBoardScreen.Initial)
        }
    }

    fun retry() {
        load()
    }

    fun load() {
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
        observable.updateUi(LoadUiState.Empty)
    }

}