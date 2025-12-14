package com.example.calculadoraimc.feature.home.view

import MainCard
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.rounded.HelpOutline
import androidx.compose.material.icons.rounded.History
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculadoraimc.feature.home.components.IMCCalculatorContainer
import com.example.calculadoraimc.feature.home.model.IMCData
import com.example.calculadoraimc.feature.home.viewmodel.HomeViewModel
import com.example.calculadoraimc.ui.theme.CalculadoraIMCTheme
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material3.MaterialTheme


/**
 * Gemini - início
 * Prompt:
 * Passe todos os componentes para ser dinamico e utilizar os themes light e dark
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(
    viewModel: HomeViewModel,
    onNavigateToHistory: () -> Unit,
    onNavigateToHelp: () -> Unit,
    onToggleTheme: () -> Unit
) {
    val colors = MaterialTheme.colorScheme

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Calculadora",
                            color = colors.onPrimary,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal
                        )
                        Text(
                            text = "IMC & Saúde",
                            color = colors.onPrimary,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onToggleTheme) {
                        Icon(
                            imageVector = Icons.Default.DarkMode,
                            contentDescription = "Alternar tema",
                            tint = colors.onPrimary
                        )
                    }

                    IconButton(
                        onClick = onNavigateToHistory,
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = colors.onPrimary.copy(alpha = 0.15f)
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.History,
                            contentDescription = "Histórico",
                            tint = colors.onPrimary
                        )
                    }

                    IconButton(
                        onClick = onNavigateToHelp,
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = colors.onPrimary.copy(alpha = 0.15f)
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.HelpOutline,
                            contentDescription = "Ajuda",
                            tint = colors.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent
                ),
                modifier = Modifier.statusBarsPadding()
            )
        },
        containerColor = Color.Transparent
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            colors.primary,
                            colors.secondary,
                            colors.background
                        )
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(horizontal = 20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(Modifier.height(8.dp))

                // Entrada de dados
                IMCCalculatorContainer(
                    height = viewModel.height,
                    onHeightChange = viewModel::onHeightChange,
                    weight = viewModel.weight,
                    onWeightChange = viewModel::onWeightChange,
                    age = viewModel.age,
                    onAgeChange = viewModel::onAgeChange,
                    gender = viewModel.gender,
                    onGenderSelected = viewModel::onGenderSelected,
                    activityLevel = viewModel.activityLevel,
                    onActivityLevelSelected = viewModel::onActivityLevelSelected,
                    waist = viewModel.waist,
                    onWaistChange = viewModel::onWaistChange,
                    neck = viewModel.neck,
                    onNeckChange = viewModel::onNeckChange,
                    hip = viewModel.hip,
                    onHipChange = viewModel::onHipChange,
                    validationState = viewModel.validationState,
                    onCalculate = viewModel::calculate
                )

                Spacer(Modifier.height(24.dp))

                viewModel.resultIMC?.let {
                    HomeContent(it)
                }

                Spacer(Modifier.height(32.dp))
            }
        }
    }
}

@Composable
fun HomeContent(result: IMCData) {
    // Card Principal
    MainCard(result)

    Spacer(Modifier.height(24.dp))
}

@Preview(showBackground = true)
@Composable
private fun HomePreview() {
    CalculadoraIMCTheme {
        // A preview está quebrada porque Home requer um ViewModel que não pode ser instanciado aqui.
    }
}

/**
 * Gemini - fim
 */