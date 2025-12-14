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

    var activityLevel by mutableStateOf<ActivityLevel?>(null)
        private set

    var resultIMC by mutableStateOf<IMCData?>(null)
        private set

    var validationState by mutableStateOf(ValidationState())
        private set

    fun onHeightChange(newHeight: String) {
        if (newHeight.length <= 3) {
            height = newHeight
            validationState = validationState.copy(heightError = false)
        }
    }

    fun onWeightChange(newWeight: String) {
        if (newWeight.length <= 7) {
            weight = newWeight
            validationState = validationState.copy(weightError = false)
        }
    }

    fun onAgeChange(newAge: String) {
        if (newAge.length <= 3) {
            age = newAge
            validationState = validationState.copy(ageError = false)
        }
    }

    fun onGenderSelected(selectedGender: Gender) {
        gender = selectedGender
        validationState = validationState.copy(genderError = false)
    }

    fun onActivityLevelSelected(level: ActivityLevel) {
        activityLevel = level
        validationState = validationState.copy(activityLevelError = false)
    }

    fun calculate() {
        val heightValue = height.toIntOrNull()
        val weightValue = weight.toDoubleOrNull()
        val ageValue = age.toIntOrNull()

        val isHeightInvalid = heightValue == null || heightValue <= 0
        val isWeightInvalid = weightValue == null || weightValue <= 0
        val isAgeInvalid = ageValue == null || ageValue <= 0
        val isGenderInvalid = gender == null
        val isActivityLevelInvalid = activityLevel == null

        validationState = ValidationState(
            heightError = isHeightInvalid,
            weightError = isWeightInvalid,
            ageError = isAgeInvalid,
            genderError = isGenderInvalid,
            activityLevelError = isActivityLevelInvalid
        )

        val hasError = isHeightInvalid || isWeightInvalid || isAgeInvalid || isGenderInvalid || isActivityLevelInvalid
        if (hasError) {
            return
        }

        // Como todos os campos foram validados, podemos usar os valores não nulos com segurança.
        val validatedHeight = heightValue!!
        val validatedWeight = weightValue!!
        val validatedAge = ageValue!!
        val validatedGender = gender!!
        val validatedActivityLevel = activityLevel!!

        Calculations.calculateIMC(height = height, weight = weight) { imcResult ->

            val bmr = Calculations.calculateBMR(validatedWeight, validatedHeight, validatedAge, validatedGender)
            val idealWeight = Calculations.calculateIdealWeight(validatedHeight, validatedGender)
            val dailyCaloricNeed = Calculations.calculateDailyCaloricNeed(bmr, validatedActivityLevel)

            val finalResult = imcResult.copy(
                bmr = bmr,
                idealWeight = idealWeight,
                dailyCaloricNeed = dailyCaloricNeed
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
            dailyCaloricNeed = result.dailyCaloricNeed
        )
        repository.insertHistory(history)
    }
}
