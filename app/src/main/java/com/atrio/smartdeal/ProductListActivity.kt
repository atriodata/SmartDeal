package com.atrio.smartdeal

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.LinearLayout
import com.atrio.smartdeal.adapter.CotegoryAdapter
import com.atrio.smartdeal.adapter.DataAdapter
import com.atrio.smartdeal.model.ProductModel
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_product_list.*

class ProductListActivity : AppCompatActivity() {

    lateinit var rootRef: DatabaseReference
//    lateinit var arr: List<ProductModel>

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
//                arr = List<ProductModel>()
                val mproduct=ArrayList<ProductModel>()
                Log.i("mylist56565656",dataSnapshot.value.toString())

                for (dataSnapshot1 in dataSnapshot.children) {

//                    var subkey = dataSnapshot1.child("Productname").value.toString()
//                            arr?.add(subkey)
                    val productModel=dataSnapshot1.getValue(ProductModel::class.java)
                    Log.i("mylist5656",productModel.toString())

//                    mproduct.add(productModel.Productname)
                    var subkey = productModel!!.Productname
                    Log.i("mylist56",subkey)
                    mproduct.add(productModel)
//                    arr.ad(subkey)
//                    arr.addAll()
                }
                rv_product_list.adapter = DataAdapter(mproduct, R.layout.category_oneview, applicationContext)
            }


        })

    }
}
