package com.kliachenko.presentation.core.views

import android.os.Parcel
import android.os.Parcelable
import android.view.View

class VisibilitySaveState: View.BaseSavedState {

    private var visibility: Int = View.VISIBLE

    fun save(view: View) {
        this.visibility = view.visibility
    }

    fun restore(view: View){
        view.visibility = visibility
    }

    constructor(superState: Parcelable) : super(superState)

    private constructor(parcelIn: Parcel) : super(parcelIn) {
        visibility = parcelIn.readInt()
    }

    override fun writeToParcel(out: Parcel, flags: Int) {
        super.writeToParcel(out, flags)
        out.writeInt(visibility)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<VisibilitySaveState> {
        override fun createFromParcel(parcel: Parcel): VisibilitySaveState = VisibilitySaveState(parcel)
        override fun newArray(size: Int): Array<VisibilitySaveState?> = arrayOfNulls(size)
    }
}