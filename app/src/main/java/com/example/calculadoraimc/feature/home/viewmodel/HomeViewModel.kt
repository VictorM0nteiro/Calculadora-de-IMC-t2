package com.example.calculadoraimc.feature.home.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calculadoraimc.data.local.entity.IMCHistory
import com.example.calculadoraimc.data.repository.IMCHistoryRepository
import com.example.calculadoraimc.datasource.Calculations
import com.example.calculadoraimc.feature.home.model.ActivityLevel
import com.example.calculadoraimc.feature.home.model.Gender
import com.example.calculadoraimc.feature.home.model.IMCData
import kotlinx.coroutines.launch
import java.util.Date

data class ValidationState(
    val heightError: Boolean = false,
    val weightError: Boolean = false,
    val ageError: Boolean = false,
    val genderError: Boolean = false,
    val activityLevelError: Boolean = false
)

class HomeViewModel(private val repository: IMCHistoryRepository) : ViewModel() {

    var height by mutableStateOf("")
        private set
    var weight by mutableStateOf("")
        private set
    var age by mutableStateOf("")
        private set
    var gender by mutableStateOf<Gender?>(null)
        private set
    var waist by mutableStateOf("")
        private set
    var neck by mutableStateOf("")
        private set
    var hip by mutableStateOf("")
        private set
    var activityLevel by mutableStateOf<ActivityLevel?>(null)
        private set
    var resultIMC by mutableStateOf<IMCData?>(null)
        private set
    var validationState by mutableStateOf(ValidationState())
        private set

    // Funções de input
    fun onHeightChange(value: String) { if (value.length <= 3) { height = value; validationState = validationState.copy(heightError = false) } }
    fun onWeightChange(value: String) { if (value.length <= 7) { weight = value; validationState = validationState.copy(weightError = false) } }
    fun onAgeChange(value: String) { if (value.length <= 3) { age = value; validationState = validationState.copy(ageError = false) } }
    fun onGenderSelected(value: Gender) { gender = value; validationState = validationState.copy(genderError = false) }
    fun onActivityLevelSelected(value: ActivityLevel) { activityLevel = value; validationState = validationState.copy(activityLevelError = false) }
    fun onWaistChange(value: String) { waist = value }
    fun onNeckChange(value: String) { neck = value }
    fun onHipChange(value: String) { hip = value }

    // Função de cálculo principal
    fun calculate() {
        val h = height.toIntOrNull()
        val w = weight.toDoubleOrNull()
        val a = age.toIntOrNull()
        val waistValue = waist.toDoubleOrNull()
        val neckValue = neck.toDoubleOrNull()
        val hipValue = hip.toDoubleOrNull() // pode ser null para homens

        val isHeightInvalid = h == null || h <= 0
        val isWeightInvalid = w == null || w <= 0
        val isAgeInvalid = a == null || a <= 0
        val isGenderInvalid = gender == null
        val isActivityLevelInvalid = activityLevel == null
        val isWaistInvalid = waistValue == null || waistValue <= 0
        val isNeckInvalid = neckValue == null || neckValue <= 0
        val isHipInvalid = gender == Gender.FEMALE && hipValue == null

        validationState = ValidationState(
            heightError = isHeightInvalid,
            weightError = isWeightInvalid,
            ageError = isAgeInvalid,
            genderError = isGenderInvalid,
            activityLevelError = isActivityLevelInvalid
        )

        if (isHeightInvalid || isWeightInvalid || isAgeInvalid || isGenderInvalid || isActivityLevelInvalid || isWaistInvalid || isNeckInvalid || isHipInvalid) return

        // Valores validados
        val validatedHeight = h!!
        val validatedWeight = w!!
        val validatedAge = a!!
        val validatedGender = gender!!
        val validatedActivityLevel = activityLevel!!

        // Calcula IMC
        Calculations.calculateIMC(height = height, weight = weight) { imcResult ->

            val bmr = Calculations.calculateBMR(validatedWeight, validatedHeight, validatedAge, validatedGender)
            val idealWeight = Calculations.calculateIdealWeight(validatedHeight, validatedGender)
            val dailyCaloricNeed = Calculations.calculateDailyCaloricNeed(bmr, validatedActivityLevel)

            val bodyFat = Calculations.calculateBodyFat(
                gender = validatedGender,
                height = validatedHeight,
                waist = waistValue!!,
                neck = neckValue!!,
                hip = hipValue
            )

            val finalResult = imcResult.copy(
                bmr = bmr,
                idealWeight = idealWeight,
                dailyCaloricNeed = dailyCaloricNeed,
                bodyFat = bodyFat
            )

            resultIMC = finalResult
            saveCalculation(finalResult)
        }
    }

    private fun saveCalculation(result: IMCData) = viewModelScope.launch {
        val history = IMCHistory(
            date = Date(),
            weight = weight.toDoubleOrNull() ?: 0.0,
            height = height.toIntOrNull() ?: 0,
            imc = result.imcValue,
            imcClassification = result.classification,
            age = age.toIntOrNull() ?: 0,
            gender = gender,
            bmr = result.bmr,
            idealWeight = result.idealWeight,
            dailyCaloricNeed = result.dailyCaloricNeed,
            bodyFat = result.bodyFat
        )
        repository.insertHistory(history)
    }
}
