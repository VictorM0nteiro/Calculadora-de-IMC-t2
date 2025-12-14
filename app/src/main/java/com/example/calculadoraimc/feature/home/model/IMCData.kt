package com.example.calculadoraimc.feature.home.model

data class IMCData(
    val imc: String,
    val classification: String,
    val imcValue: Double,
    val bmr: Int? = null,
    val idealWeight: String? = null,
    val dailyCaloricNeed: Int? = null,
    val bodyFat: Float?
)