package com.example.covidapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.covidapp.Adapter.DataAdapter
import com.example.covidapp.DataBase.CovidAppconect
import com.example.covidapp.DataBase.entity.Resultado

class VerDataActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_data)
        initRecyclerView()
    }
    fun initRecyclerView(){
        val recyclerView = findViewById<RecyclerView>(R.id.rvListadoData)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = DataAdapter(ObtenerBusqueda())
    }
    fun ObtenerBusqueda() : List<Resultado> {
        val listaData = CovidAppconect.database.resultadosDao().getAll()
        return listaData
    }
}