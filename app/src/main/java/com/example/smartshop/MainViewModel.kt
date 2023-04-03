package com.example.smartshop

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.smartshop.api.repository.ShopRepository
import com.example.smartshop.model.*

class MainViewModel: ViewModel() {
    val repository = ShopRepository()
    val error = MutableLiveData<String>()
    val progress = MutableLiveData<Boolean>()
    val categoriesData = MutableLiveData<List<CategoryModel>>()
    val offersData = MutableLiveData<List<OffersModel>>()
    val productData = MutableLiveData<List<ProductModel>>()
    val topProductData = MutableLiveData<List<TopProductModel>>()

    fun getOffers() {
        repository.getOffers(error, offersData)
    }

    fun getTopProducts() {
        repository.getTopProducts(error, topProductData)
    }
    fun getHistoryProducts() {
        repository.getHistoryProducts(error, topProductData)
    }

    fun getCategories() {
        repository.getCategories(error, categoriesData,progress)
    }

    fun getProductsByCategory(id: Int) {
        repository.getProductsByCategory(id, error, productData)
    }
    fun getProductsByIds(ids: List<Int>) {
        repository.getProductsByIdsTop(ids, error, progress, topProductData)
    }
}