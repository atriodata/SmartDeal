package com.atrio.smartdeal.adapter

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.atrio.smartdeal.R
import android.widget.ImageView
import android.widget.Toast
import com.atrio.smartdeal.CompareActivity
import com.atrio.smartdeal.ProductData
import com.atrio.smartdeal.ProductListActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.category_oneview.view.*
import kotlinx.android.synthetic.main.custom_recycle_main.view.*


/**
 * Created by Arpita Patel on 2/3/2018.
 */
class CotegoryAdapter(val dataList: List<String>, val rowLayout: Int, val context: Context) : RecyclerView.Adapter<CotegoryAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CotegoryAdapter.ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(rowLayout, parent, false)
        return ProductViewHolder(view)
    }


    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(dataList[position], position)

    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class ProductViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        fun bind(product: String, position: Int) {
            var spacereplace=product.replace("_"," ")
            itemView.tv_catogory.text = spacereplace

            itemView.setOnClickListener(View.OnClickListener {

                val clickintent = Intent(itemView.context, ProductListActivity::class.java)
                clickintent.putExtra("product_name",product)
                Log.i("sendintent",product)
                itemView.context.startActivity(clickintent)

            })
        }

    }
}