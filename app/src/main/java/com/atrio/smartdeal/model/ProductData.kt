package com.atrio.smartdeal

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ProductData(val product_title: String,
                       val can_compare: Boolean,
                       val product_lowest_price: Int,
                       val product_link: String,
                       val product_id: String,
                       val product_category: String,
                       val product_sub_category: String,
                       val product_rating: Double,
                       val product_image: String)