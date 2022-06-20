package com.example.covidapp.DataBase.Dao

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

//    @Query("SELECT resultados.departamento, COUNT (resultados.departamento) FROM Resultados WHERE resultados.fecha_corte =:fecha_busqueda ")
//    fun BusquedaFecha(fecha_busqueda : Integer) : List<DataDepartamentos>
}