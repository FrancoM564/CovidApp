package com.example.covidapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
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
        showDatePickerDialog()

        binding.etIngresarfecha.setOnClickListener {
            showDatePickerDialog()
        }
    }

    private fun showDatePickerDialog(){
        val datePicker = DatePickerFragment {day, month, year -> onDateSelected(day,month,year)}
        datePicker.show(supportFragmentManager, "date ")
    }

    private fun onDateSelected(day:Int,mes:Int,year:Int) {
        //TODO AQUI DECIDEN EL FORMATO DE LA FECHA PARA LA QUERY
        val month = mes + 1
        var tempDay : String = day.toString()
        var tempMonth : String = month.toString()
        if(day<10)
            tempDay = "0"+day
        if(month<10)
            tempMonth = "0"+month
        fecha = (year.toString() + tempMonth + tempDay).toInt()
        println("CAM" + fecha)
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
            listaData.map{
                when(it.departamento){
                    "AMAZONAS" -> it
                    "ANCASH" -> it
                    "APURIMAC" -> it
                    "AREQUIPA" -> it
                    "AYACUCHO" -> it
                    "CAJAMARCA" -> it
                    "CALLAO" -> it
                    "CUSCO" -> it
                    "HUANCAVELICA" -> it
                    "HUANUCO" -> it
                    "ICA" -> it
                    "JUNIN" -> it
                    "LA LIBERTAD" -> it
                    "LAMBAYEQUE" -> it
                    "LIMA" -> it
                    "LORETO" -> it
                    "MADRE DE DIOS" -> it
                    "MOQUEGUA" -> it
                    "PASCO" -> it
                    "PIURA" -> it
                    "PUNO" -> it
                    "SAN MARTIN" -> it
                    "TACNA" -> it
                    "TUMBES" -> it
                    "UCAYALI" -> it
                    else -> null
                }
            }
            if(listaData.size>0){
                println("ENTROOOOOO")
                binding.rvListadodata.visibility = View.VISIBLE
                binding.tvNohaydatadisponible.visibility = View.GONE
                println("HOLA" + listaData)
                lifecycleScope.launch(Dispatchers.Main){
                    binding.rvListadodata.adapter = DataAdapter(listaData)
                }
            }else{
                println("SALIOOOOO")
                binding.tvNohaydatadisponible.visibility = View.VISIBLE
                binding.rvListadodata.visibility = View.GONE
            }
        }
    }

    suspend fun ObtenerBusqueda() : List<DataDepartamentos> {
        listaData = CovidAppconect.database.resultadosDao().BusquedaFecha(fecha)
        return listaData
    }
}