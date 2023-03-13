package com.example.smartshop.model

import java.io.Serializable

data class ProductModel(
    val id: Int,
    val name: String,
    val price: String,
    val image: String,
    var cartCount: Int = 1,
): Serializable