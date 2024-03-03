package com.kliachenko.presentation.core

interface ShowList<T : Any> {

    fun show(list: List<T>)
}