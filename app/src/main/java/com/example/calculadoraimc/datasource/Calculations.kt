package com.example.calculadoraimc.datasource

import android.annotation.SuppressLint
import com.example.calculadoraimc.feature.home.model.ActivityLevel
import com.example.calculadoraimc.feature.home.model.Gender
import com.example.calculadoraimc.feature.home.model.IMCData
import kotlin.math.roundToInt
import kotlin.math.log10

/**
 * Claude - início
 * Prompt:
 * Estou desenvolvendo um aplicativo Android em Kotlin usando Jetpack Compose e arquitetura MVVM.
 * Preciso criar uma classe utilitária para fazer os calculos de indicadores de saúde.
 * Gere funcoes para calculo de IMC, levando em consideraçao altura, peso, idade e crie uma classificacao de: abaixo do peso, normal, sobrepeso, obesidade grau 1,2 e 3
 *
 */
object Calculations {

    @SuppressLint("DefaultLocale")
    fun calculateIMC(height: String, weight: String, response: (IMCData) -> Unit) {
        val heightValue = height.toIntOrNull()
        val weightValue = weight.toDoubleOrNull()

        if (heightValue == null || weightValue == null || heightValue <= 0 || weightValue <= 0) {
            response(IMCData(imc = "---", classification = "Valores inválidos", imcValue = 0.0, bodyFat = null))
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

        response(IMCData(imcFormatted, imcClassification, imc, bodyFat = null))
    }

    /**
     * Gemini - início
     * Prompt:
     * Estou desenvolvendo um aplicativo Android em Kotlin usando Jetpack Compose e arquitetura MVVM.
     * Preciso criar uma função para calcular a Taxa Metabólica Basal
     * Leve em consideração sexo (masculino ou feminino), idade, peso e altura.
     * A função deve retornar o valor da TMB como um número inteiro
     * Explique a fórmula utilizada em comentários
     */
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
     * Gemini - fim
     */

    /**
     * Gemini - início
     * Prompt:
     *  * Preciso criar uma função para estimar a necessidade calórica diária.
     *  * O cálculo deve ser baseado na TMB multiplicada por um fator de atividade física.
     *  * Considere níveis de atividade como sedentário, leve, moderado e intenso.
     *  Alem disso,  Preciso criar uma função para calcular o peso ideal de uma pessoa.
     *  * Leve em consideração o sexo masculino ou feminino e a altura
     */

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

    /**
     * Gemini - fim
     */
    @SuppressLint("DefaultLocale")
    fun calculateBodyFat(
        gender: Gender,
        height: Int,
        waist: Double,
        neck: Double,
        hip: Double? = null
    ): Float {
        // Calcula o valor para o logaritmo
        val bodyFatValue = when (gender) {
            Gender.MALE -> {
                val waistNeck = waist - neck
                if (waistNeck <= 0) {
                    return Float.NaN
                }
                86.010 * log10(waistNeck) - 70.041 * log10(height.toDouble()) + 36.76
            }
            Gender.FEMALE -> {
                requireNotNull(hip) { "Quadril obrigatório para mulheres" }
                val waistHipNeck = waist + hip - neck
                if (waistHipNeck <= 0) {
                    return Float.NaN
                }
                163.205 * log10(waistHipNeck) - 97.684 * log10(height.toDouble()) - 78.387
            }
        }

        // Retorna o valor calculado como Float
        return bodyFatValue.toFloat()
    }
}
/**
 * Claude - Fim
 */
