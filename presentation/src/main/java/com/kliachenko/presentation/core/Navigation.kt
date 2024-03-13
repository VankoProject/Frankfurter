package com.kliachenko.presentation.core

import javax.inject.Inject

interface Navigation : UiObservable<Screen> {

    fun clear()

    class Base @Inject constructor() : UiObservable.Abstract<Screen>(Screen.Empty), Navigation {
        override fun clear() {
            cache = Screen.Empty
        }
    }
}