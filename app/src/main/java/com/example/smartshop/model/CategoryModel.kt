package com.example.smartshop.model

import java.io.Serializable

data class CategoryModel(
    val id: Int,
    val title: String,
    val icon: String,
): Serializable
