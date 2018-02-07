package com.atrio.smartdeal

import `in`.android2.com.autocompletetextview_demo.DataAdapter
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.atrio.smartdeal.adapter.CustomAdapter
import com.atrio.smartdeal.model.ProductList
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.view.ViewGroup
import android.widget.*
import java.util.*
import android.widget.TextView
import android.widget.ArrayAdapter
import android.app.Activity
import android.text.Editable
import android.text.TextWatcher
import android.widget.AutoCompleteTextView
import retrofit2.adapter.rxjava.Result.response


class MainActivity : AppCompatActivity() {

    var searchItem: String? = null
    val BASE_URL = "https://price-api.datayuge.com/"
    val API_KEY = "u6SMILRT5RfgvgnXqQlX3qbrd67ahnZdvhi"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadSuggestions()


        rv_android_list.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        rv_android_list.setHasFixedSize(true)

    }

    private fun loadSuggestions() {
        val retrofitobject = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
        val apiService = retrofitobject.create(ApiInterface::class.java)
        val call = apiService.getProductList("R", API_KEY)
        call.enqueue(object : Callback<ProductList> {
            override fun onFailure(call: Call<ProductList>, t: Throwable) {
                Log.i("goterror", t.localizedMessage)
            }

            override fun onResponse(call: Call<ProductList>, response: Response<ProductList>) {
                Log.i("getproductList", response.message())
                Log.i("getproductList11", response.code().toString())
                Log.i("getproductList1", response.body().toString())
                Log.i("getproductList2", response.body()?.data?.size.toString())
                /*  var mproductlist = response.body()
                  if (mproductlist != null) {
                      searchadapter.addAll(mproductlist.data)
  //                    rv_android_list.adapter = CustomAdapter(mproductlist.data, R.layout.custom_recycle_main, applicationContext)
  //                    searcitem!!.setText("Redmi")
                      serch_item.threshold = 1
                      serch_item.setAdapter(searchadapter)*/
//                    searcitem!!.setAdapter(searchadapter)

                Log.d("Async Data RemoteData", "Got REMOTE DATA " + response.body()?.data?.size)

                val str = ArrayList<String>()
               /* var mproductlist = response.body()!!.data
                if (mproductlist != null) {
                    str.add(mproductlist.indexOf("product_title"))
                }*/
                for (s in response.body()!!.data) {
                    str.add(s.product_title)
                }
                Log.i("gotdata23", str.toString())

//                    val storeTV = (context as Activity).findViewById(R.id.store) as AutoCompleteTextView

                val adapteo = ArrayAdapter(this@MainActivity, android.R.layout.simple_dropdown_item_1line, str.toTypedArray())
                serch_item.setAdapter(adapteo)
//                Log.i("gotdata23", adapteo.getItem(1))
            }

        })
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
                var mproductlist = response.body()
                if (mproductlist != null) {
                    rv_android_list.adapter = CustomAdapter(mproductlist.data, R.layout.custom_recycle_main, applicationContext)
//                    serch_item.adapter=DataAdapter(applicationContext,mproductlist.data)

                    Log.i("gotdata23", mproductlist.data.get(1).product_title)
                }
            }

        })
    }
}


