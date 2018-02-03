package com.atrio.smartdeal

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import com.atrio.smartdeal.adapter.CustomAdapter
import com.atrio.smartdeal.model.ProductList
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {
    var searchItem: String? = null
    val BASE_URL = "https://price-api.datayuge.com/"
    val API_KEY = "u6SMILRT5RfgvgnXqQlX3qbrd67ahnZdvhi"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        serch_item.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View, event: MotionEvent): Boolean {
                if (event.action == MotionEvent.ACTION_UP) {
                    Log.i("HelloApp", "Welcome")
                    searchItem = serch_item.text.toString()
                    getProductSearch()
                }
                return false
            }

        })

        rv_android_list.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        rv_android_list.setHasFixedSize(true)
    }
    private fun getProductSearch() {

        val retrofitobject = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
        val apiService = retrofitobject.create(ApiInterface::class.java)
        val call = apiService.getProductList(searchItem!!, API_KEY)
        call.enqueue(object : Callback<ProductList> {
            override fun onFailure(call: Call<ProductList>, t: Throwable) {
                Log.i("goterror", t.localizedMessage)
            }

            override fun onResponse(call: Call<ProductList>, response: Response<ProductList>) {
                Log.i("getproductList", response.message())
                Log.i("getproductList11", response.code().toString())
                Log.i("getproductList1", response.body().toString())
                Log.i("getproductList2", response.body()?.data?.size.toString())
                var mproductlist=response.body()
                rv_android_list.adapter=CustomAdapter(mproductlist!!.data,R.layout.custom_recycle_main, applicationContext)
                Log.i("gotdata23", mproductlist.toString())
            }

        })
    }
}


