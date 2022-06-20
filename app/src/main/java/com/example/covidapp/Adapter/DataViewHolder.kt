package com.example.covidapp.Adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.covidapp.DataBase.entity.Resultado
import com.example.covidapp.R
import com.example.covidapp.databinding.ItemDataBinding
import com.example.covidapp.room.DataDepartamentos

class DataViewHolder(view : View) : RecyclerView.ViewHolder(view) {

    val binding = ItemDataBinding.bind(view)

    fun render(resultadoModel: Resultado){
        binding.tvDepartamento.text = resultadoModel.departamento
        binding.tvCantidad.text = resultadoModel.edad.toString()
    }
}