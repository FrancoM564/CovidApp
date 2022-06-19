package com.example.covidapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import com.example.covidapp.DataBase.CovidAppconect
import com.example.covidapp.DataBase.entity.Resultado
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    val url = "https://files.minsa.gob.pe/s/eRqxR35ZCxrzNgr/download"
    var btnSincro : Button? = null
    var btnLimpiar : Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnSincro = findViewById(R.id.bt_sincronizar)
        btnLimpiar = findViewById(R.id.bt_limpiar)

        btnSincro!!.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
            val xd = CovidAppconect.database.resultadosDao().getAll()
            Log.d("sd",xd.toString())

            //sincronizar()
        } }

        btnLimpiar!!.setOnClickListener{
            lifecycleScope.launch(Dispatchers.IO){
                limpiar()
            }
        }

    }

    fun sincronizar(){
        val connection = URL(url).openConnection() as HttpURLConnection

        try {
            val data = connection.inputStream.bufferedReader()

            var linea = data.readLine()
            var resultado : Resultado
            linea = data.readLine()
            resultado = deserializar(linea)
            CovidAppconect.database.resultadosDao().insertarResultado(resultado)

            /*while(data.readLine().also{linea = it}!=null){
                resultado = deserializar(linea)
                CovidAppconect.database.resultadosDao().insertarResultado(resultado)
            }*/

           //Log.d("dasd",CovidAppconect.database.resultadosDao().getCount().toString())

        }catch(ex: IOException){
            Log.d("Exception",ex.toString())
        }
    }

    fun deserializar (linea : String): Resultado{
        val datosLinea = linea.split(";")

        var fechacorte : Int?
        var departamento = datosLinea[1]
        var provincia = datosLinea[2]
        var distrito = datosLinea[3]
        var metodo = datosLinea[4]
        var edad : Int?
        var sexo = datosLinea[6]
        var fecharesultado : Int?
        var ubigeo : Int?
        var idpersona : Int?

        if (datosLinea[0]==""){
            fechacorte = null
        }else{
            fechacorte = datosLinea[0].toInt()
        }

        if (datosLinea[5]==""){
            edad = null
        }else{
            edad = datosLinea[5].toInt()
        }

        if (datosLinea[7]==""){
            fecharesultado = null
        }else{
            fecharesultado = datosLinea[7].toInt()
        }

        if (datosLinea[8]==""){
            ubigeo = null
        }else{
            ubigeo = datosLinea[8].toInt()
        }

        if (datosLinea[9]==""){
            idpersona = null
        }else{
            idpersona = datosLinea[9].toInt()
        }

        var resultado = Resultado(0,fechacorte,departamento,provincia,distrito,metodo,edad,sexo,fecharesultado,
                                ubigeo,idpersona)

        return resultado
    }

    fun limpiar(){
        CovidAppconect.database.resultadosDao().nukeTable()
    }
}