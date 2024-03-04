package com.kliachenko.presentation.dashboard

interface ClickActions: RemovePair {

    fun retry()
}

interface RemovePair {

    fun remove(itemId: String)

}