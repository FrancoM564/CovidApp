package com.example.covidapp

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import com.example.covidapp.DataBase.CovidAppconect
import com.example.covidapp.DataBase.entity.Resultado
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    val url = "https://files.minsa.gob.pe/s/eRqxR35ZCxrzNgr/download"
    var btnSincro : Button? = null
    var btnLimpiar : Button? = null
    var btnData : Button? = null
    var pgBar : ProgressBar? = null
    var txtBar : TextView? = null
    var estaSincro : Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnSincro = findViewById(R.id.bt_sincronizar)
        btnLimpiar = findViewById(R.id.bt_limpiar)
        btnData = findViewById(R.id.bt_verdata)
        pgBar = findViewById(R.id.pgBar1)
        txtBar = findViewById(R.id.txPgBar)
        pgBar!!.visibility = View.GONE

        btnSincro!!.setOnClickListener {
            sincronizar()
        }

        btnLimpiar!!.setOnClickListener{
            if(estaSincro){
                Log.d("s","Sincronizando.....")
            }else{
                lifecycleScope.launch (Dispatchers.IO){
                    limpiar()
                }
                btnSincro!!.isEnabled = true
            }
        }

        btnData!!.setOnClickListener {
            startActivity(Intent(this, VerDataActivity::class.java))
        }
    }

    fun sincronizar():Boolean{
        estaSincro = true
        lifecycleScope.launch(Dispatchers.IO){
            runOnUiThread(Runnable {
                btnSincro!!.isEnabled = false
                pgBar!!.visibility = View.VISIBLE
                pgBar!!.isIndeterminate = true
                txtBar!!.text = "Cargando datos"
            })
            val connection = URL(url).openConnection() as HttpURLConnection
            try {
                val data = connection.inputStream.bufferedReader()
                var linea : String?
                data.readLine()
                var resultado : Resultado

                while(data.readLine().also{linea = it}!=null){
                    resultado = deserializar(linea)
                    CovidAppconect.database.resultadosDao().insertarResultado(resultado)
                }

            }catch(ex: IOException){
                Log.d("Exception",ex.toString())
            }

            runOnUiThread(Runnable {
                estaSincro = false
                pgBar!!.isIndeterminate = false
                pgBar!!.progress = 100
                txtBar!!.text = "Datos cargados correctamente!"
            })

            delay(3000)

            runOnUiThread(Runnable {
                pgBar!!.visibility = View.GONE
                txtBar!!.visibility = View.GONE
            })
        }
        return true
    }

    fun deserializar (linea : String?): Resultado{

        val datosLinea = linea!!.split(";")

        var fechacorte : Int? = null
        var departamento = datosLinea[1]
        var provincia = datosLinea[2]
        var distrito = datosLinea[3]
        var metodo = datosLinea[4]
        var edad : Int? = null
        var sexo = datosLinea[6]
        var fecharesultado : Int? = null
        var ubigeo : Int? = null
        var idpersona : Int? = null

        if (datosLinea[0]!=""){
            fechacorte = datosLinea[0].toInt()
        }

        if (datosLinea[5]!=""){
            edad = datosLinea[5].toInt()
        }

        if (datosLinea[7]!=""){
            fecharesultado = datosLinea[7].toInt()
        }

        if (datosLinea[8]!=""){
            ubigeo = datosLinea[8].toInt()
        }

        if (datosLinea[9]!=""){
            idpersona = datosLinea[9].toInt()
        }

        var resultado = Resultado(null,fechacorte,departamento,provincia,distrito,metodo,edad,sexo,fecharesultado,
                                ubigeo,idpersona)

        return resultado
    }

    fun limpiar(){
        var nelementos = CovidAppconect.database.resultadosDao().getCount()
        if(nelementos == 0){
            Log.d("s","No hay que borrar")
        }else{
            CovidAppconect.database.resultadosDao().nukeTable()
            Log.d("s","Borrado")
        }
    }

    fun tempVerdatos(){
        //aca hagan sus cosas
        Log.d("sdasas",CovidAppconect.database.resultadosDao().getCount().toString())
    }
}