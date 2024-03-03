package com.kliachenko.presentation.core.views

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

class CustomImageView : AppCompatImageView, ChangeVisibility {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(
        context: Context,
        attrs: AttributeSet,
        defStyleAttr: Int = com.google.android.material.R.attr.circularProgressIndicatorStyle,
    ) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onSaveInstanceState(): Parcelable? {
        return super.onSaveInstanceState()?.let {
            val state = VisibilitySavedState(it)
            state.save(this)
            return state
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val restoreState = state as VisibilitySavedState?
        super.onRestoreInstanceState(restoreState?.superState)
        state?.restore(this)
    }

    override fun show() {
        visibility = VISIBLE
    }

    override fun hide() {
        visibility = GONE
    }

}