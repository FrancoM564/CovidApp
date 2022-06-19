package com.example.covidapp.DataBase.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "Resultados")
data class Resultado(
    @PrimaryKey(autoGenerate = true)
    val id : Int? = 0,
    val fecha_corte : Int?,
    val departamento : String,
    val provincia : String,
    val distrito : String,
    val metodo : String,
    val edad : Int?,
    val sexo : String,
    val fecha_resultado : Int?,
    val ubigeo : Int?,
    val id_persona : Int?
)