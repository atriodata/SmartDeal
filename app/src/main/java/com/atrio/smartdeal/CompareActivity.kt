package com.atrio.smartdeal

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.atrio.smartdeal.R
import com.atrio.smartdeal.model.ComapePrice
import com.atrio.smartdeal.model.ProductList
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_compare.*
import kotlinx.android.synthetic.main.custom_recycle_main.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CompareActivity : AppCompatActivity() {
    val BASE_URL = "https://price-api.datayuge.com/"
    val API_KEY = "u6SMILRT5RfgvgnXqQlX3qbrd67ahnZdvhi"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compare)
        val product_image=intent.getStringExtra("product_image")
        product_name.text=intent.getStringExtra("product_name")
        Picasso.with(this).load(product_image).resize(90, 90).into(imageView)
        getComparePrice()
    }

    private fun getComparePrice() {
        val productId = intent.getStringExtra("product_id")
        val retrofitobject = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
        val apiService = retrofitobject.create(ApiInterface::class.java)
        val call = apiService.getProductPrice(API_KEY, productId)
        call.enqueue(object : Callback<ComapePrice> {
            override fun onFailure(call: Call<ComapePrice>, t: Throwable) {
                Log.i("goterror", t.localizedMessage)
            }

            override fun onResponse(call: Call<ComapePrice>, response: Response<ComapePrice>) {

                Log.i("getproductList", response.message())
                Log.i("getproductList11", response.code().toString())
                Log.i("getproductList1", response.body().toString())
                var mpricelist=response.body()
                if (mpricelist!!.amazon!=null || mpricelist.flipkart!=null || mpricelist.ebay!=null || mpricelist.paytm!=null || mpricelist.snapdeal!=null)
                {
                product1.text="Amazon Price : Rs."+mpricelist.amazon
                product2.text="FlipKart Price : Rs."+mpricelist.flipkart
                product3.text="Ebay Price : Rs."+mpricelist.ebay
                product4.text="Paytm Price : Rs."+mpricelist.paytm
                product5.text="SnapDeal Price : Rs."+mpricelist.snapdeal
            }
            }

        })
    }
}
