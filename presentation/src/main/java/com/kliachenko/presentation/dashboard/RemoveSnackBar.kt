package com.kliachenko.presentation.dashboard

import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.kliachenko.presentation.R

class RemoveSnackBar(private val removePair: RemovePair) {

    fun showSnackBar(
        view: View,
        pairId: String,
    ) {
        Snackbar.make(view, R.string.remove_pair, Snackbar.LENGTH_SHORT)
            .setAction(R.string.confirm) {
                removePair.remove(pairId)
            }
            .show()
    }
}