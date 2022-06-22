package com.example.covidapp.DataBase.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "Resultados")
data class Resultado(
    @PrimaryKey(autoGenerate = true)
    val id : Int? = 0,
    val departamento : String,
    val fecha_resultado : Int?
)