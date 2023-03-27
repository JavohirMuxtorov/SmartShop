package com.example.smartshop.model

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class TopProductModel(
    val id: Int,
    val name: String,
    val price: String,
    val image: String,
    var cartCount: Int = 1,
): Serializable