package com.example.calculadoraimc.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

/**
 * Gemini - início
 * Prompt:
 * Crie todos os meus temas utilizando esse meu Color.kt como referência:
 */

private val LightColorScheme = lightColorScheme(
    // Cores principais
    primary = AccentColor,
    onPrimary = TextOnAccent,
    primaryContainer = AccentSoft,
    onPrimaryContainer = TextPrimary,

    // Secundárias
    secondary = PrimaryLight,
    onSecondary = TextOnPrimary,
    secondaryContainer = AccentLight,
    onSecondaryContainer = TextPrimary,

    // Fundo e superfícies
    background = BackgroundLight,
    onBackground = TextPrimary,
    surface = SurfaceColor,
    onSurface = TextPrimary,
    surfaceVariant = SurfaceVariant,
    onSurfaceVariant = TextSecondary,

    // Terciária
    tertiary = PrimaryMedium,
    onTertiary = TextOnPrimary,

    // Estados / erros
    error = RedLevel,
    onError = TextOnPrimary
)

private val DarkColorScheme = darkColorScheme(
    // Cores principais
    primary = AccentLight,
    onPrimary = TextPrimary,
    primaryContainer = AccentColor,
    onPrimaryContainer = TextOnPrimary,

    // Secundárias
    secondary = PrimaryMedium,
    onSecondary = TextOnPrimary,
    secondaryContainer = PrimaryDark,
    onSecondaryContainer = TextOnPrimary,

    // Fundo e superfícies
    background = BackgroundDark,
    onBackground = TextOnPrimary,
    surface = PrimaryDark,
    onSurface = TextOnPrimary,
    surfaceVariant = PrimaryMedium,
    onSurfaceVariant = AccentSoft,

    // Terciária
    tertiary = AccentColor,
    onTertiary = TextOnPrimary,

    // Estados / erros
    error = RedLevel,
    onError = TextOnPrimary
)

@Composable
fun CalculadoraIMCTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

/**
 * Gemini - fim
 */
