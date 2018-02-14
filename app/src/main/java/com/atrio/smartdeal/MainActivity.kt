package com.atrio.smartdeal

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.LinearLayout
import android.widget.SearchView
import com.atrio.smartdeal.adapter.CustomAdapter
import com.atrio.smartdeal.model.ProductList
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    val BASE_URL = "https://price-api.datayuge.com/"
    val API_KEY = "u6SMILRT5RfgvgnXqQlX3qbrd67ahnZdvhi"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        search.setOnQueryTextListener(this)

        rv_android_list.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        rv_android_list.setHasFixedSize(true)
    }
    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
       }

    override fun onQueryTextChange(newText: String?): Boolean {
        getProductSearch(newText)
        return false
    }
    private fun getProductSearch(newText: String?) {

        val retrofitobject = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
        val apiService = retrofitobject.create(ApiInterface::class.java)
        val call = apiService.getProductList(newText!!, API_KEY)
        call.enqueue(object : Callback<ProductList> {
            override fun onFailure(call: Call<ProductList>, t: Throwable) {
                Log.i("goterror", t.localizedMessage)
            }

            override fun onResponse(call: Call<ProductList>, response: Response<ProductList>) {
                val mproductlist=response.body()

                if (mproductlist!=null){
                rv_android_list.adapter=CustomAdapter(mproductlist.data,R.layout.custom_recycle_main, applicationContext)

            } }

        })
    }
}


