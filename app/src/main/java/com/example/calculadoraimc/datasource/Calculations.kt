package com.example.calculadoraimc.datasource

import android.annotation.SuppressLint
import com.example.calculadoraimc.feature.home.model.ActivityLevel
import com.example.calculadoraimc.feature.home.model.Gender
import com.example.calculadoraimc.feature.home.model.IMCData
import kotlin.math.roundToInt

object Calculations {

    @SuppressLint("DefaultLocale")
    fun calculateIMC(height: String, weight: String, response: (IMCData) -> Unit) {
        val heightValue = height.toIntOrNull()
        val weightValue = weight.toDoubleOrNull()

        if (heightValue == null || weightValue == null || heightValue <= 0 || weightValue <= 0) {
            response(IMCData(imc = "---", classification = "Valores inválidos", imcValue = 0.0))
            return
        }

        val heightInMeters = heightValue / 100.0
        val imc = weightValue / (heightInMeters * heightInMeters)
        val imcFormatted = String.format("%.1f", imc)

        val imcClassification = when {
            imc < 18.5 -> "Abaixo do peso"
            imc < 25 -> "Peso normal"
            imc < 30 -> "Sobrepeso"
            imc < 35 -> "Obesidade Grau I"
            imc < 40 -> "Obesidade Grau II"
            else -> "Obesidade Grau III"
        }

        response(IMCData(imcFormatted, imcClassification, imc))
    }

    /**
     * Calcula a Taxa Metabólica Basal (TMB) usando a fórmula de Mifflin-St Jeor.
     * Fórmula para homens: (10 * peso em kg) + (6.25 * altura em cm) - (5 * idade em anos) + 5
     * Fórmula para mulheres: (10 * peso em kg) + (6.25 * altura em cm) - (5 * idade em anos) - 161
     */
    fun calculateBMR(weight: Double, height: Int, age: Int, gender: Gender): Int {
        val bmr = (10 * weight) + (6.25 * height) - (5 * age)
        return if (gender == Gender.MALE) {
            (bmr + 5).roundToInt()
        } else {
            (bmr - 161).roundToInt()
        }
    }

    /**
     * Calcula o Peso Ideal usando a Fórmula de Devine.
     * Fórmula para homens: 50 kg + 2.3 kg por cada polegada acima de 5 pés.
     * Fórmula para mulheres: 45.5 kg + 2.3 kg por cada polegada acima de 5 pés.
     */
    @SuppressLint("DefaultLocale")
    fun calculateIdealWeight(height: Int, gender: Gender): String {
        val heightInInches = height / 2.54
        val fiveFeetInInches = 60

        if (heightInInches <= fiveFeetInInches) {
            return if (gender == Gender.MALE) "~50 kg" else "~45.5 kg"
        }

        val inchesOverFiveFeet = heightInInches - fiveFeetInInches
        val baseWeight = if (gender == Gender.MALE) 50.0 else 45.5
        val idealWeight = baseWeight + (2.3 * inchesOverFiveFeet)

        val lowerBound = idealWeight * 0.9
        val upperBound = idealWeight * 1.1

        return "${String.format("%.1f", lowerBound)} - ${String.format("%.1f", upperBound)} kg"
    }

    /**
     * Estima a necessidade calórica diária com base na TMB e no nível de atividade física.
     * Fórmula: Necessidade Calórica = TMB * Fator de Atividade.
     */
    fun calculateDailyCaloricNeed(bmr: Int, activityLevel: ActivityLevel): Int {
        return (bmr * activityLevel.factor).roundToInt()
    }
}
