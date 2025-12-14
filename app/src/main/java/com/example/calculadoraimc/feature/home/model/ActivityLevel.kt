package com.example.calculadoraimc.feature.home.model

enum class ActivityLevel(val displayName: String, val factor: Double) {
    SEDENTARY("Sedentário", 1.2),
    LIGHTLY_ACTIVE("Levemente Ativo", 1.375),
    MODERATELY_ACTIVE("Moderadamente Ativo", 1.55),
    VERY_ACTIVE("Muito Ativo", 1.725),
    EXTRA_ACTIVE("Extremamente Ativo", 1.9)
}
