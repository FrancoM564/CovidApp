package com.example.covidapp

import android.os.Build.VERSION_CODES.P
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log.d
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
    private var listaData : List<DataDepartamentos> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.etIngresarfecha.setOnClickListener {
            showDatePickerDialog()
        }
    }

    private fun showDatePickerDialog(){
        val datePicker = DatePickerFragment {day, month, year -> onDateSelected(day,month,year)}
        datePicker.show(supportFragmentManager, "date ")
    }

    private fun onDateSelected(day:Int,month:Int,year:Int) {
        //TODO AQUI DECIDEN EL FORMATO DE LA FECHA PARA LA QUERY
        fecha = year + month*10000 + day*1000000
        binding.etIngresarfecha.text = Editable.
        Factory.getInstance().newEditable("${day}/${month}/${year}")
        initRecyclerView()
    }

    fun initRecyclerView(){
        val manager = LinearLayoutManager(this)
        val decoration = DividerItemDecoration(this, manager.orientation)
        binding.rvListadodata.layoutManager = manager
        lifecycleScope.launch(Dispatchers.IO){
            listaData = ObtenerBusqueda()
            println("HOLA" + listaData)
            lifecycleScope.launch(Dispatchers.Main){
                binding.rvListadodata.adapter = DataAdapter(listaData)
            }
        }
    }

    suspend fun ObtenerBusqueda() : List<DataDepartamentos> {
        listaData = CovidAppconect.database.resultadosDao().BusquedaFecha(fecha)
        return listaData
    }
}