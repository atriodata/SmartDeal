package com.atrio.smartdeal

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import android.widget.SimpleAdapter
import com.atrio.smartdeal.adapter.CotegoryAdapter
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_category.*


class CategoryActivity : AppCompatActivity() {
    lateinit var rootRef: DatabaseReference
    var arr: ArrayList<String>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        rootRef = FirebaseDatabase.getInstance().reference
        rv_category_list.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        rv_category_list.setHasFixedSize(true)

        getCategoryList()
    }

    private fun getCategoryList() {
        val query_catlist = rootRef.child("CategoryList").orderByKey()
        query_catlist.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError?) {

            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                arr = ArrayList<String>()

                for(dataSnapshot1 in dataSnapshot.children){
                    var subkey = dataSnapshot1.getKey()
                    arr?.add(subkey)
                }
                rv_category_list.adapter=CotegoryAdapter(arr!!,R.layout.category_oneview, applicationContext)
            }



        })
    }
}
