package com.atrio.smartdeal.RecyclerAdapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.graphics.Color
import com.atrio.smartdeal.R
import com.atrio.smartdeal.model.SearchDataModel


class DataAdapter (private val dataList : ArrayList<SearchDataModel>, private val listener : Listener) : RecyclerView.Adapter<DataAdapter.ViewHolder>() {

    interface Listener {
        fun onItemClick(android : SearchDataModel)
    }

    private val colors : Array<String> = arrayOf("#EF5350", "#EC407A", "#AB47BC", "#7E57C2", "#5C6BC0", "#42A5F5")

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(dataList[position], listener, colors, position)
    }

    override fun getItemCount(): Int = dataList.count()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.custom_recycledata, parent, false)

        return ViewHolder(view)
    }

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {

        fun bind(android: SearchDataModel, listener: Listener, colors : Array<String>, position: Int) {
/*
            itemView.tv_name.text = android.name
            itemView.tv_version.text = android.version
            itemView.tv_api_level.text = android.apiLevel
            itemView.setBackgroundColor(Color.parseColor(colors[position % 6]))

            itemView.setOnClickListener{ listener.onItemClick(android) }*/
        }
    }
}