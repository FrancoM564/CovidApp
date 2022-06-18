package com.example.covidapp.DataBase

import android.app.Application
import androidx.room.Room
import com.example.covidapp.DataBase.conect.ResultadosDb

class CovidAppconect : Application(){

    companion object{
        lateinit var database: ResultadosDb
    }

    override fun onCreate() {
        super.onCreate()
        CovidAppconect.database = Room.databaseBuilder(this,
        ResultadosDb::class.java,
        "covidapp-db").build()
    }
}