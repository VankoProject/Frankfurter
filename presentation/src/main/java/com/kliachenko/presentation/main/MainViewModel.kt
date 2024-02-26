package com.kliachenko.presentation.main

import com.kliachenko.presentation.core.BaseViewModel
import com.kliachenko.presentation.core.RunAsync
import com.kliachenko.presentation.core.UiObservable

class MainViewModel(
    private val observable: UiObservable<Any>,
    runAsync: RunAsync,
) : BaseViewModel(runAsync)