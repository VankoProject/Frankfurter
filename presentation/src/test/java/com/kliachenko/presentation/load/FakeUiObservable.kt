package com.kliachenko.presentation.load

import org.junit.Assert

class FakeUiObservable : LoadUiObservable {

    private var actualUiState: LoadingUiState = LoadingUiState.Empty
    private var actualObserver: UpdateUi<LoadingUiState> = UpdateUi.Empty

    override fun updateUi(uiState: LoadingUiState) {
        actualUiState = uiState
    }

    override fun updateObserver(observer: UpdateUi<LoadingUiState>) {
        actualObserver = observer
    }

    fun checkProgress() {
        val expected: LoadingUiState = LoadingUiState.Progress
        Assert.assertEquals(expected, actualUiState)

    }

    fun checkObserver(expected: UpdateUi<LoadingUiState>) {
        Assert.assertEquals(expected, actualObserver)
    }

    fun checkError() {
        val expected: LoadingUiState = LoadingUiState.Error(message = "No internet connection")
        Assert.assertEquals(expected, actualUiState)
    }

}