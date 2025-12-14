package com.example.calculadoraimc.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.calculadoraimc.feature.home.model.Gender
import java.util.Date

@Entity(tableName = "imc_history")
data class IMCHistory(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val date: Date,
    val weight: Double,
    val height: Int,
    val imc: Double,
    val imcClassification: String,
    val age: Int,
    val gender: Gender?,
    val bmr: Int?,
    val idealWeight: String?,
    val dailyCaloricNeed: Int?,
    val bodyFat: Float?
)
