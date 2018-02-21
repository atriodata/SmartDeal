package com.atrio.smartdeal

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.LinearLayout
import com.atrio.smartdeal.adapter.CotegoryAdapter
import com.atrio.smartdeal.adapter.DataAdapter
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_product_list.*

class ProductListActivity : AppCompatActivity() {

    lateinit var rootRef: DatabaseReference
    var arr: ArrayList<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)

        val productName = intent.getStringExtra("product_name")
        rootRef = FirebaseDatabase.getInstance().reference
        rv_product_list.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        rv_product_list.setHasFixedSize(true)

        getProductList(productName)

    }

    private fun getProductList(productName: String) {
        val query_catlist = rootRef.child("CategoryList").child(productName).orderByKey()
        query_catlist.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {

            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                arr = ArrayList<String>()
                for (dataSnapshot1 in dataSnapshot.children) {

                    var subkey = dataSnapshot1.child("Productname").value.toString()
                            arr?.add(subkey)
                }
                rv_product_list.adapter = DataAdapter(arr!!, R.layout.category_oneview, applicationContext)
            }


        })

    }
}
