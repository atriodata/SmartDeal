package com.atrio.smartdeal

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import com.atrio.smartdeal.InterfaceModel.RequestInterface
import com.atrio.smartdeal.RecyclerAdapter.DataAdapter
import com.atrio.smartdeal.model.SearchDataModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private val BASE_URL = "https://price-api.datayuge.com/"
    private var mAndroidArrayList: ArrayList<SearchDataModel>? = null

    private var mAdapter: DataAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecyclerView()

        loadJSON()
    }

    private fun loadJSON() {
        val requestInterface = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(RequestInterface::class.java)


    }

    private fun initRecyclerView() {
        val recyclerView = findViewById(R.id.rv_android_list) as RecyclerView
        //adding a layoutmanager
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        recyclerView.setHasFixedSize(true)


    }
}
