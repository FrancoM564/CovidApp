package com.example.covidapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.covidapp.Adapter.DataAdapter
import com.example.covidapp.DataBase.CovidAppconect
import com.example.covidapp.DataBase.entity.Resultado
import com.example.covidapp.databinding.ActivityVerDataBinding

class VerDataActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVerDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerDataBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()
    }
    fun initRecyclerView(){
        val manager = LinearLayoutManager(this)
        val decoration = DividerItemDecoration(this, manager.orientation)
        binding.rvListadoData.layoutManager = manager
        binding.rvListadoData.adapter = DataAdapter(ObtenerBusqueda())
        binding.rvListadoData.addItemDecoration(decoration)
    }
    fun ObtenerBusqueda() : List<Resultado> {
        val listaData = CovidAppconect.database.resultadosDao().getAll()
        return listaData
    }
}