package com.example.covidapp.DataBase.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.covidapp.DataBase.entity.Resultado

@Dao
interface ResultadosDao{

    @Insert
    suspend fun insertarResultado(resultado: Resultado)

    @Query("SELECT  * FROM Resultados")
    suspend fun getAll() : List<Resultado>

}