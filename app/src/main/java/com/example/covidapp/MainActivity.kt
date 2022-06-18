package com.example.covidapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.lifecycle.lifecycleScope
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
                val connection = URL(url).openConnection() as HttpURLConnection

                try {
                    val data = connection.inputStream.bufferedReader()
                    var linea : String?
                    while(data.readLine().also{linea = it}!=null){
                        Log.d("SDadad",linea.toString())
                        Thread.sleep(50)
                    }
                }catch(ex: IOException){
                    Log.d("Exception",ex.toString())
                }
        } }

    }

    fun sincronizar(){

    }
}