package com.atrio.smartdeal

import com.atrio.smartdeal.model.ProductList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Arpita Patel on 2/1/2018.
 */
interface ApiInterface {

    @GET("/api/v1/compare/search")

    fun getProductList(@Query("product") product:String,
                       @Query("api_key") api_key:String):Call<ProductList>

}