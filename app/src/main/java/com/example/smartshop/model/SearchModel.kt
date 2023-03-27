package com.example.smartshop.model

import java.io.Serializable

data class SearchModel(
    val id: Int,
    val name: String,
    val price: String,
    val image: String,
): Serializable
