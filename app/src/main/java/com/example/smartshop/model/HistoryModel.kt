package com.example.smartshop.model

import android.os.Parcel
import android.os.Parcelable

data class HistoryModel(
    val text:String
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(dest: Parcel, flags: Int): Unit = with(dest) {
        this.writeString(text)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<HistoryModel> {
        override fun createFromParcel(parcel: Parcel): HistoryModel {
            return HistoryModel(parcel)
        }

        override fun newArray(size: Int): Array<HistoryModel?> {
            return arrayOfNulls(size)
        }
    }
}
