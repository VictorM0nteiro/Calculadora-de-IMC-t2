package com.example.calculadoraimc.feature.home.model

enum class BodyFatClassification(
    val maleRange: ClosedFloatingPointRange<Double>,
    val femaleRange: ClosedFloatingPointRange<Double>,
    val label: String
) {
    ESSENTIAL(2.0..5.0, 10.0..13.0, "Gordura essencial"),
    ATHLETES(6.0..13.0, 14.0..20.0, "Atletas"),
    FITNESS(14.0..17.0, 21.0..24.0, "Fitness"),
    AVERAGE(18.0..24.0, 25.0..31.0, "Médio"),
    OBESE(25.0..60.0, 32.0..60.0, "Obesidade")
}