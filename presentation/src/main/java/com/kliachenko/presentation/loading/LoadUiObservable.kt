package com.kliachenko.presentation.loading

import com.kliachenko.presentation.core.UiObservable

interface LoadUiObservable : UiObservable<LoadUiState> {

    class Base : UiObservable.Abstract<LoadUiState>(LoadUiState.Empty), LoadUiObservable

}
