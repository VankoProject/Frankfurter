package com.kliachenko.presentation.core

interface ShowList<T : ListItem> {

    fun show(list: List<T>)
}