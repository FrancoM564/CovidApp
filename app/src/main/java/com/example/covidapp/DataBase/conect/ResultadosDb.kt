package com.example.covidapp.DataBase.conect

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.covidapp.DataBase.Dao.ResultadosDao
import com.example.covidapp.DataBase.entity.Resultado

@Database(
    entities = [Resultado::class],
    version = 1,
    exportSchema = false
)
abstract class ResultadosDb : RoomDatabase(){

    abstract fun resultadosDao() : ResultadosDao

}