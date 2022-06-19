package com.example.covidapp.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.covidapp.DataBase.entity.Resultado
import com.example.covidapp.R
import com.example.covidapp.room.DataDepartamentos

class DataAdapter(private val DataList: List<Resultado>) : RecyclerView.Adapter<DataViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return DataViewHolder(layoutInflater.inflate(R.layout.item_data, parent, false))
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val item = DataList[position]
        holder.render(item)
    }

    override fun getItemCount(): Int = DataList.size

}