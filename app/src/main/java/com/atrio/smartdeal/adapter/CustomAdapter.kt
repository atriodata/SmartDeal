package com.atrio.smartdeal.adapter

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.atrio.smartdeal.R
import android.widget.ImageView
import android.widget.Toast
import com.atrio.smartdeal.CompareActivity
import com.atrio.smartdeal.ProductData
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.custom_recycle_main.view.*


/**
 * Created by Arpita Patel on 2/3/2018.
 */
class CustomAdapter(val dataList: List<ProductData>, val rowLayout: Int, val context: Context) : RecyclerView.Adapter<CustomAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomAdapter.ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(rowLayout, parent, false)
        return ProductViewHolder(view)
    }


    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        /*   Picasso.with(context).load(dataList[position].product_image).resize(90, 90).into(holder.productImage)
           holder.productTitle.text = "Product :" + dataList[position].product_title
           holder.productPrice.text = "Price : " + dataList[position].product_lowest_price*/
        holder.bind(dataList[position], position)

    }

    override fun getItemCount(): Int {
        return dataList.size
    }
   /* fun filter(text: String){

    }
*/
    class ProductViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        /*     var productImage: ImageView
             var productTitle: TextView
             var productPrice: TextView

            init {
                productImage = v.findViewById(R.id.img_prd) as ImageView
                productTitle = v.findViewById(R.id.tv_name) as TextView
                productPrice = v.findViewById(R.id.tv_price) as TextView
            }
    */
        fun bind(product: ProductData, position: Int) {
            Picasso.with(itemView.context).load(product.product_image).resize(90, 90).into(itemView.img_prd)
            itemView.tv_name.text = product.product_title
            itemView.tv_price.text = "MinPrice : " + product.product_lowest_price
            itemView.setOnClickListener(View.OnClickListener {
                Toast.makeText(itemView.context, "click" + position, Toast.LENGTH_SHORT).show()
                val clickintent = Intent(itemView.context, CompareActivity::class.java)
                clickintent.putExtra("product_id",product.product_id)
                clickintent.putExtra("product_name",product.product_title)
                clickintent.putExtra("product_image",product.product_image)

                itemView.context.startActivity(clickintent)

            })
        }

    }
}