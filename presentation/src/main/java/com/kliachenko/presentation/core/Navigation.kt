package com.kliachenko.presentation.core

interface Navigation : UiObservable<Screen> {

    fun clear()

    class Base : UiObservable.Abstract<Screen>(Screen.Empty), Navigation {
        override fun clear() {
            cache = Screen.Empty
        }
    }
}