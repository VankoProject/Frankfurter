package com.kliachenko.presentation.loading

import com.kliachenko.presentation.core.BaseViewModel
import com.kliachenko.presentation.core.Clear
import com.kliachenko.presentation.core.RunAsync

class LoadViewModel(
    private val observable: LoadUiObservable,
    private val repository: MainRepository,
    private val navigation: Navigation,
    private val clear: Clear,
    runAsync: RunAsync
): BaseViewModel(runAsync) {

    fun init() {

    }

    fun retry() {

    }

    fun startGettingUpdates(observer: UpdateUi<LoadUiState>) {

    }

    fun stopGettingUpdates() {

    }


}