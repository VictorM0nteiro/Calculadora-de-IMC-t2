package com.example.calculadoraimc.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.Color.Companion.Yellow

private val AppColorScheme = lightColorScheme(
    primary = Yellow,
    onPrimary = Black,
    background = White,
    onBackground = Black,
    surface = White,
    onSurface = Black,
    secondary = Yellow,
    onSecondary = Black,
    tertiary = Yellow,
    onTertiary = Black
)

@Composable
fun CalculadoraIMCTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = AppColorScheme,
        typography = Typography,
        content = content
    )
}
