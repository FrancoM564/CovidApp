package com.example.covidapp.DataBase.Dao

import android.text.Editable
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.covidapp.DataBase.entity.Resultado
import com.example.covidapp.room.DataDepartamentos

@Dao
interface ResultadosDao{

    @Insert
    fun insertarResultado(resultado: Resultado)

    @Query("SELECT * FROM Resultados")
    fun getAll() : List<Resultado>

    @Query("SELECT COUNT(*) FROM Resultados")
    fun getCount() : Int

    @Query("DELETE FROM Resultados")
    fun nukeTable()

    @Query("SELECT departamento, COUNT (departamento) as cantidad FROM Resultados WHERE fecha_resultado =:fecha_resultado GROUP BY departamento")
    fun BusquedaFecha(fecha_resultado: Int) : List<DataDepartamentos>
}