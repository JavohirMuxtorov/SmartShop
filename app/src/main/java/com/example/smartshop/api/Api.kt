package com.example.smartshop.api

import com.example.smartshop.model.BaseResponse
import com.example.smartshop.model.CategoryModel
import com.example.smartshop.model.OffersModel
import com.example.smartshop.model.ProductModel
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {
    @GET("get_offers")
    fun getOffers(): Observable<BaseResponse<List<OffersModel>>>

    @GET("get_categories")
    fun getCategories(): Observable<BaseResponse<List<CategoryModel>>>

    @GET("get_top_products")
    fun getTopProducts(): Observable<BaseResponse<List<ProductModel>>>

    @GET("get_products/{category_id}")
    fun getCategoryProducts(@Path("category_id") categoryId: Int): Observable<BaseResponse<List<ProductModel>>>
}