package com.example.smartshop.api

import com.example.smartshop.model.*
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface Api {
    @GET("get_offers")
    fun getOffers(): Observable<BaseResponse<List<OffersModel>>>

    @GET("get_categories")
    fun getCategories(): Observable<BaseResponse<List<CategoryModel>>>

    @GET("get_top_products")
    fun getTopProducts(): Observable<BaseResponse<List<TopProductModel>>>

    @GET("get_products/{category_id}")
    fun getCategoryProducts(@Path("category_id") categoryId: Int): Observable<BaseResponse<List<ProductModel>>>

    @POST("get_products_by_ids")
    fun getProductsByIds(@Body request: GetProductsByIdsRequest):Observable<BaseResponse<List<TopProductModel>>>
}