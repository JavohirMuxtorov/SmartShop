package com.example.smartshop.model

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi
import androidx.core.os.ParcelCompat.readArrayList

data class UserModel (
    val id: String ="",
    val name: String = "",
    val email: String = "",
    val img: String="",
    var startPassword: String ="false"
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString() !!,
    ) {
    }

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int): Unit = with(dest) {
        this.writeString(id)
        this.writeString(name)
        this.writeString(email)
        this.writeString(img)
        this.writeString(startPassword)
    }

    companion object CREATOR : Parcelable.Creator<UserModel> {
        override fun createFromParcel(parcel: Parcel): UserModel {
            return UserModel(parcel)
        }

        override fun newArray(size: Int): Array<UserModel?> {
            return arrayOfNulls(size)
        }
    }
}