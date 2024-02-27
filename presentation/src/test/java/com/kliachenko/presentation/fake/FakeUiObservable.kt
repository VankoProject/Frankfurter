package com.kliachenko.presentation.fake

import com.kliachenko.presentation.core.UpdateUi
import com.kliachenko.presentation.loading.LoadUiObservable
import com.kliachenko.presentation.loading.LoadUiState
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals

class FakeUiObservable : LoadUiObservable {

    private var actualUiState: LoadUiState = LoadUiState.Empty
    private var actualObserver: UpdateUi<LoadUiState> = UpdateUi.Empty()

    override fun updateUi(uiState: LoadUiState) {
        actualUiState = uiState
    }

    override fun updateObserver(observer: UpdateUi<LoadUiState>) {
        actualObserver = observer
    }

    fun checkProgress() {
        val expected: LoadUiState = LoadUiState.Progress
        assertEquals(expected, actualUiState)

    }

    fun checkObserver(expected: UpdateUi<LoadUiState>) {
        assertEquals(expected, actualObserver)
    }

    fun checkError() {
        val expected: LoadUiState = LoadUiState.Error(message = "No internet connection")
        assertEquals(expected, actualUiState)
    }

    fun checkEmpty(expected: UpdateUi<LoadUiState>) {
        assertNotEquals(expected, actualObserver)
    }

}