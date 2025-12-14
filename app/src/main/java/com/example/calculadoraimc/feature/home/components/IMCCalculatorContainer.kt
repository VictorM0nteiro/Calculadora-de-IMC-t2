package com.example.calculadoraimc.feature.home.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculadoraimc.feature.home.model.ActivityLevel
import com.example.calculadoraimc.feature.home.model.Gender
import com.example.calculadoraimc.feature.home.viewmodel.ValidationState
import com.example.calculadoraimc.ui.theme.AccentColor
import com.example.calculadoraimc.ui.theme.AccentLight
import com.example.calculadoraimc.ui.theme.AccentSoft
import com.example.calculadoraimc.ui.theme.CalculadoraIMCTheme
import com.example.calculadoraimc.ui.theme.GradientEnd
import com.example.calculadoraimc.ui.theme.GradientStart
import com.example.calculadoraimc.ui.theme.TextPrimary
import com.example.calculadoraimc.ui.theme.TextSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IMCCalculatorContainer(
    height: String,
    onHeightChange: (String) -> Unit,
    weight: String,
    onWeightChange: (String) -> Unit,
    age: String,
    onAgeChange: (String) -> Unit,
    gender: Gender?,
    onGenderSelected: (Gender) -> Unit,
    activityLevel: ActivityLevel?,
    onActivityLevelSelected: (ActivityLevel) -> Unit,
    validationState: ValidationState,
    onCalculate: () -> Unit
) {
    var isDropdownExpanded by remember { mutableStateOf(false) }

    // Card com efeito glassmorphism
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 20.dp,
                shape = RoundedCornerShape(28.dp),
                ambientColor = AccentColor.copy(alpha = 0.1f),
                spotColor = AccentColor.copy(alpha = 0.15f)
            )
            .clip(RoundedCornerShape(28.dp))
            .background(Color.White.copy(alpha = 0.95f))
            .padding(24.dp)
    ) {
        Column {
            // Título da seção
            Text(
                text = "Seus dados",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary
            )

            Spacer(Modifier.height(20.dp))

            // Inputs de Altura e Peso
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ModernTextField(
                    modifier = Modifier.weight(1f),
                    value = height,
                    onValueChange = onHeightChange,
                    label = "Altura",
                    placeholder = "cm",
                    isError = validationState.heightError
                )
                ModernTextField(
                    modifier = Modifier.weight(1f),
                    value = weight,
                    onValueChange = onWeightChange,
                    label = "Peso",
                    placeholder = "kg",
                    isError = validationState.weightError,
                    keyboardType = KeyboardType.Decimal
                )
            }

            Spacer(Modifier.height(16.dp))

            // Input de Idade
            ModernTextField(
                modifier = Modifier.fillMaxWidth(),
                value = age,
                onValueChange = onAgeChange,
                label = "Idade",
                placeholder = "anos",
                isError = validationState.ageError
            )

            Spacer(Modifier.height(20.dp))

            // Seletor de sexo modernizado
            Text(
                text = "Sexo biológico",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = TextSecondary
            )

            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .then(
                        if (validationState.genderError) {
                            Modifier.border(
                                width = 1.dp,
                                color = Color(0xFFEF4444),
                                shape = RoundedCornerShape(16.dp)
                            )
                        } else Modifier
                    ),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                GenderButton(
                    modifier = Modifier.weight(1f),
                    text = "Masculino",
                    isSelected = gender == Gender.MALE,
                    onClick = { onGenderSelected(Gender.MALE) }
                )
                GenderButton(
                    modifier = Modifier.weight(1f),
                    text = "Feminino",
                    isSelected = gender == Gender.FEMALE,
                    onClick = { onGenderSelected(Gender.FEMALE) }
                )
            }

            Spacer(Modifier.height(20.dp))

            // Seletor de Nível de Atividade
            Text(
                text = "Nível de atividade física",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = TextSecondary
            )

            Spacer(Modifier.height(8.dp))

            ExposedDropdownMenuBox(
                expanded = isDropdownExpanded,
                onExpandedChange = { isDropdownExpanded = !isDropdownExpanded }
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    readOnly = true,
                    value = activityLevel?.displayName ?: "",
                    onValueChange = {},
                    placeholder = {
                        Text(
                            "Selecione seu nível",
                            color = TextSecondary.copy(alpha = 0.6f)
                        )
                    },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isDropdownExpanded) },
                    shape = RoundedCornerShape(16.dp),
                    isError = validationState.activityLevelError,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = AccentColor,
                        unfocusedBorderColor = Color(0xFFE2E8F0),
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color(0xFFF8FAFC)
                    )
                )
                ExposedDropdownMenu(
                    expanded = isDropdownExpanded,
                    onDismissRequest = { isDropdownExpanded = false }
                ) {
                    ActivityLevel.values().forEach { level ->
                        DropdownMenuItem(
                            text = { Text(level.displayName) },
                            onClick = {
                                onActivityLevelSelected(level)
                                isDropdownExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(Modifier.height(28.dp))

            // Botão Calcular com gradiente
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                onClick = onCalculate,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),
                shape = RoundedCornerShape(16.dp),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(GradientStart, GradientEnd)
                            ),
                            shape = RoundedCornerShape(16.dp)
                        ),
                    contentAlignment = androidx.compose.ui.Alignment.Center
                ) {
                    Text(
                        text = "Calcular",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
private fun ModernTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    isError: Boolean,
    keyboardType: KeyboardType = KeyboardType.Number
) {
    Column(modifier = modifier) {
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = TextSecondary
        )
        Spacer(Modifier.height(6.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    placeholder,
                    color = TextSecondary.copy(alpha = 0.5f)
                )
            },
            shape = RoundedCornerShape(14.dp),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            isError = isError,
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = AccentColor,
                unfocusedBorderColor = Color(0xFFE2E8F0),
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color(0xFFF8FAFC),
                errorBorderColor = Color(0xFFEF4444),
                errorContainerColor = Color(0xFFFEF2F2)
            )
        )
    }
}

@Composable
private fun GenderButton(
    modifier: Modifier = Modifier,
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) AccentColor else Color(0xFFF1F5F9),
        label = "backgroundColor"
    )
    val contentColor by animateColorAsState(
        targetValue = if (isSelected) Color.White else TextSecondary,
        label = "contentColor"
    )

    Button(
        modifier = modifier.height(48.dp),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = contentColor
        ),
        shape = RoundedCornerShape(14.dp),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = if (isSelected) 4.dp else 0.dp
        )
    ) {
        Text(
            text = text,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun IMCCalculatorContainerPreview() {
    CalculadoraIMCTheme {
        var height by remember { mutableStateOf("") }
        var weight by remember { mutableStateOf("") }
        var age by remember { mutableStateOf("") }
        var gender by remember { mutableStateOf<Gender?>(null) }
        var activityLevel by remember { mutableStateOf<ActivityLevel?>(null) }

        IMCCalculatorContainer(
            height = height, onHeightChange = { height = it },
            weight = weight, onWeightChange = { weight = it },
            age = age, onAgeChange = { age = it },
            gender = gender, onGenderSelected = { gender = it },
            activityLevel = activityLevel, onActivityLevelSelected = { activityLevel = it },
            validationState = ValidationState(),
            onCalculate = {}
        )
    }
}