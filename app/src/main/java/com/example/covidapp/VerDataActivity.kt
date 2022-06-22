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

            if(listaData.size>0){
                println("ENTROOOOOO1")
                listaData = listaData.filter {
                    it.departamento == "AMAZONAS" ||
                            it.departamento == "ANCASH" ||
                            it.departamento == "APURIMAC" ||
                            it.departamento == "AREQUIPA" ||
                            it.departamento == "AYACUCHO" ||
                            it.departamento == "CAJAMARCA" ||
                            it.departamento == "CALLAO" ||
                            it.departamento == "CUSCO" ||
                            it.departamento == "HUANCAVELICA" ||
                            it.departamento == "HUANUCO" ||
                            it.departamento == "ICA" ||
                            it.departamento == "JUNIN" ||
                            it.departamento == "LA LIBERTAD" ||
                            it.departamento == "LAMBAYEQUE" ||
                            it.departamento == "LIMA" ||
                            it.departamento == "LORETO" ||
                            it.departamento == "MADRE DE DIOS" ||
                            it.departamento == "MOQUEGUA" ||
                            it.departamento == "PASCO" ||
                            it.departamento == "PIURA" ||
                            it.departamento == "PUNO" ||
                            it.departamento == "SAN MARTIN" ||
                            it.departamento == "TACNA" ||
                            it.departamento == "TUMBES" ||
                            it.departamento == "UCAYALI"
                }
                println("ENTROOOOOO2")
                lifecycleScope.launch(Dispatchers.Main) {
                    binding.rvListadodata.visibility = View.VISIBLE
                    binding.tvNohaydatadisponible.visibility = View.GONE
                }
                println("HOLA" + listaData)
                lifecycleScope.launch(Dispatchers.Main){
                    binding.rvListadodata.adapter = DataAdapter(listaData)
                }
            }else{
                println("SALIOOOOO")
                lifecycleScope.launch(Dispatchers.Main) {
                    binding.tvNohaydatadisponible.visibility = View.VISIBLE
                    binding.rvListadodata.visibility = View.GONE
                }
            }
        }
    }

    suspend fun ObtenerBusqueda() : List<DataDepartamentos> {
        listaData = CovidAppconect.database.resultadosDao().BusquedaFecha(fecha)
        return listaData
    }
}