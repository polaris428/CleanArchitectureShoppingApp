package com.android.cleanarchitectureshoppingapp.data.network

import com.android.cleanarchitectureshoppingapp.data.response.ProductResponse
import com.android.cleanarchitectureshoppingapp.data.response.ProductsResponse
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductApiService {
    @GET("product")
    suspend fun getProducts():Response<ProductsResponse>

    @GET("product/{productId}")
    suspend fun getProduct(@Path("productId")productId:Long):Response<ProductResponse>

}