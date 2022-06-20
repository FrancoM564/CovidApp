package com.example.covidapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.covidapp.Adapter.DataAdapter
import com.example.covidapp.DataBase.CovidAppconect
import com.example.covidapp.databinding.ActivityVerDataBinding
import com.example.covidapp.room.DataDepartamentos
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VerDataActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVerDataBinding
    private var fecha : Int = 0
    private lateinit var listaData : List<DataDepartamentos>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerDataBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btBuscar.setOnClickListener {
            fecha = binding.etIngresarfecha.text.toString().toInt()
            initRecyclerView(fecha)
        }
    }
    fun initRecyclerView(fecha: Int){
        val manager = LinearLayoutManager(this)
        val decoration = DividerItemDecoration(this, manager.orientation)
        binding.rvListadodata.layoutManager = manager
        lifecycleScope.launch(Dispatchers.IO){
            listaData = ObtenerBusqueda(fecha)
            println("HOLA" + listaData)
            lifecycleScope.launch(Dispatchers.Main){
                binding.rvListadodata.adapter = DataAdapter(listaData)
                binding.rvListadodata.addItemDecoration(decoration)
            }
        }
    }
    suspend fun ObtenerBusqueda(fecha: Int) : List<DataDepartamentos> {
        listaData = CovidAppconect.database.resultadosDao().BusquedaFecha(fecha)
        return listaData
    }
}