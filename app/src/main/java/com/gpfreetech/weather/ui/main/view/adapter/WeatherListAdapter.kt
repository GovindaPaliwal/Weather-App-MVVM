package com.gpfreetech.weather.ui.main.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gpfreetech.weather.R
import com.gpfreetech.weather.ui.main.data.model.WeatherDetail

class WeatherListAdapter(private var callback:Callback) : RecyclerView.Adapter<WeatherListAdapter.ViewHolder>() {

    private var list: List<WeatherDetail> = mutableListOf()
    //private lateinit var callback : Callback
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_temperature,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtName.text = list!![position].cityName+", "+list!![position].countryName
        holder.txtDateTime.text = list!![position].dateTime
        holder.txtTemp.text = "${list!![position].temp}"
        holder.itemView.setOnClickListener {
            this.callback.clickOnCity(list!![position])
        }
    }

    override fun getItemCount(): Int {
        return list!!.size
    }

    fun addList(list: List<WeatherDetail>){
        this.list=list
        notifyDataSetChanged()
    }

    class ViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView){
        val txtName : TextView = itemView.findViewById(R.id.text_city_name)
        val txtDateTime : TextView = itemView.findViewById(R.id.text_date_time)
        val txtTemp : TextView = itemView.findViewById(R.id.text_temperature)
    }


    interface Callback {
        fun clickOnCity(weather:WeatherDetail)
    }

}