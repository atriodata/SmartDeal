package com.atrio.smartdeal.adapter

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.*
import android.widget.TextView
import com.atrio.smartdeal.R
import android.widget.ImageView
import android.widget.Toast
import com.atrio.smartdeal.CompareActivity
import com.atrio.smartdeal.ProductData
import com.atrio.smartdeal.ProductListActivity
import com.atrio.smartdeal.model.ProductModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.category_oneview.view.*
import kotlinx.android.synthetic.main.custom_dialog.*
import kotlinx.android.synthetic.main.custom_recycle_main.view.*


/**
 * Created by Arpita Patel on 2/3/2018.
 */
class DataAdapter(val dataList: List<ProductModel>, val rowLayout: Int, val context: Context) : RecyclerView.Adapter<DataAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataAdapter.ProductViewHolder {
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
        fun bind(product: ProductModel, position: Int) {
            itemView.tv_catogory.text = product.Productname


            itemView.setOnClickListener(View.OnClickListener {

                val dialog = Dialog(itemView.context)
                dialog.setContentView(R.layout.custom_dialog)
                dialog.setTitle("Compare Price.. ")
                dialog.tv_one.text="Aldi Price:  ${product.Aldi}"
                dialog.tv_two.text="Coles Price:  ${product.Coles}"
                dialog.tv_three.text="Wools Price:  ${product.Wools}"
                //dialog.tv_one.setText()
                var wmp : WindowManager.LayoutParams  = dialog.window.attributes
                wmp.gravity= Gravity.TOP
               // wmp.gravity = Gravity.LEFT
               // wmp.x = itemView.left - (itemView.width *2)
                wmp.y = itemView.top
                dialog.show();

            })
        }

    }
}