package com.kliachenko.presentation.loading

import com.kliachenko.presentation.core.UiObservable
import javax.inject.Inject

interface LoadUiObservable : UiObservable<LoadUiState> {

    class Base @Inject constructor() : UiObservable.Abstract<LoadUiState>(LoadUiState.Empty),
        LoadUiObservable

}
