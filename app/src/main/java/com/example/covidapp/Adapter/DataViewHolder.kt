package com.example.covidapp.Adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.covidapp.DataBase.entity.Resultado
import com.example.covidapp.R
import com.example.covidapp.room.DataDepartamentos

class DataViewHolder(view : View) : RecyclerView.ViewHolder(view) {
    val nombreDepartamento = view.findViewById<TextView>(R.id.tvDepartamento)
    val cantidadDepartamento = view.findViewById<TextView>(R.id.tvCantidad)

    fun render(resultadoModel: DataDepartamentos){
        nombreDepartamento.text = resultadoModel.departamento
        cantidadDepartamento.text = resultadoModel.cantidad.toString()
    }
}