package com.atrio.smartdeal

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
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
        product1.visibility =View.GONE
        product2.visibility =View.GONE
        product3.visibility =View.GONE
        product4.visibility =View.GONE
        product5.visibility =View.GONE
        linearLayout2.visibility = View.GONE
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
                var mpricelist=response.body()
                if (!mpricelist!!.amazon.isEmpty()){
                    linearLayout2.visibility = View.VISIBLE
                    product1.visibility = View.VISIBLE
                    product1.text="Amazon Price : Rs."+mpricelist.amazon
                }else{
                    product1.visibility = View.INVISIBLE
                }
                if (!mpricelist.flipkart.isEmpty()){
                    linearLayout2.visibility = View.VISIBLE
                    product2.visibility = View.VISIBLE
                    product2.text="FlipKart Price : Rs."+mpricelist.flipkart
                }else{
                    product2.visibility = View.INVISIBLE
                }
                if (!mpricelist.ebay.isEmpty()){
                    linearLayout2.visibility = View.VISIBLE
                    product3.visibility = View.VISIBLE
                    product3.text="Ebay Price : Rs."+mpricelist.ebay
                }else{
                    product3.visibility = View.INVISIBLE
                }

                if (!mpricelist.paytm.isEmpty()){
                    linearLayout2.visibility = View.VISIBLE
                    product4.visibility = View.VISIBLE
                    product4.text="Paytm Price : Rs."+mpricelist.paytm
                }else{
                    product4.visibility = View.INVISIBLE
                }

                if (!mpricelist.snapdeal.isEmpty()){
                    linearLayout2.visibility = View.VISIBLE
                    product5.visibility = View.VISIBLE
                    product5.text="SnapDeal Price : Rs."+mpricelist.snapdeal
                }else{
                    product5.visibility = View.INVISIBLE
                }

            }


        })
    }
}
