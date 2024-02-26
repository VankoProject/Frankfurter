package com.kliachenko.presentation.core.views

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet

class CustomTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0,
) : androidx.appcompat.widget.AppCompatTextView(context, attrs, defStyleAttr), ChangeVisibility,
    ChangeText {

        init {
            freezesText = true
        }

    override fun onSaveInstanceState(): Parcelable? {
        return super.onSaveInstanceState()?.let {
            val state = VisibilitySaveState(it)
            state.save(this)
            return state
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val restoreState = state as VisibilitySaveState?
        super.onRestoreInstanceState(restoreState?.superState)
        state?.restore(this)
    }

    override fun show() {
        visibility = VISIBLE
    }

    override fun hide() {
        visibility = GONE
    }

    override fun change(text: String) {
        this.text = text
    }
}

interface ChangeText {
    fun change(text: String)
}

interface ErrorText: ChangeVisibility, ChangeText