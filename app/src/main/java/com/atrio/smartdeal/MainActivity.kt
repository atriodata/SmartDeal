package com.atrio.smartdeal

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.atrio.smartdeal.model.ProductList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    val BASE_URL="https://price-api.datayuge.com/"
    val API_KEY = "u6SMILRT5RfgvgnXqQlX3qbrd67ahnZdvhi"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i("HelloApp","Welcome")

        getProductSearch()


    }

    private fun getProductSearch() {

        var searchItem="Redmi"

        val retrofitobject= Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
        val apiService=retrofitobject.create(ApiInterface::class.java)
        val call=apiService.getProductList(searchItem,API_KEY)
        call.enqueue(object :Callback<ProductList>{
            override fun onFailure(call: Call<ProductList>, t: Throwable) {
                Log.i("goterror",t.localizedMessage)
            }

            override fun onResponse(call: Call<ProductList>, response: Response<ProductList>) {
                Log.i("getproductList", response.message())
                Log.i("getproductList11", response.code().toString())
                Log.i("getproductList1", response.body().toString())
                Log.i("getproductList2", call.toString())
            }

        })
    }
}
