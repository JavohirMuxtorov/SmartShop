package com.example.smartshop.model

data class ProductDetailModel(
    val id: Int,
    val name: String,
    val price: String,
    val image: ArrayList<String>,
    val comment: String
)
