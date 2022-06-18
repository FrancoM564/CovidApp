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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnSincro = findViewById(R.id.bt_sincronizar)

        btnSincro!!.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                sincronizar()
        } }

    }

    fun sincronizar(){
        val connection = URL(url).openConnection() as HttpURLConnection

        try {
            val data = connection.inputStream.bufferedReader()

            var linea = data.readLine()
            var resultado : Resultado

            while(data.readLine().also{linea = it}!=null){
                resultado = deserializar(linea)
                //CovidAppconect.database.resultadosDao().insertarResultado(resultado)
            }

            Log.d("dasd",CovidAppconect.database.resultadosDao().getAll().toString())

        }catch(ex: IOException){
            Log.d("Exception",ex.toString())
        }
    }

    fun deserializar (linea : String): Resultado{
        val datosLinea = linea.split(";")

        val fechacorte : Int

        var resultado = Resultado(0,datosLinea[0].toInt(),datosLinea[1],
        datosLinea[2],datosLinea[3],datosLinea[4],datosLinea[5].toInt(),
        datosLinea[6],datosLinea[7].toInt(),datosLinea[8].toInt(),datosLinea[9].toInt())
        return resultado
    }
}