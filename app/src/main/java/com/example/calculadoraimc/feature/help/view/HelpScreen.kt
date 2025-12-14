package com.example.calculadoraimc.feature.help.view

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Balance
import androidx.compose.material.icons.rounded.Calculate
import androidx.compose.material.icons.rounded.LocalDining
import androidx.compose.material.icons.rounded.LocalFireDepartment
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculadoraimc.ui.theme.AccentColor
import com.example.calculadoraimc.ui.theme.BackgroundLight
import com.example.calculadoraimc.ui.theme.GradientEnd
import com.example.calculadoraimc.ui.theme.GradientStart
import com.example.calculadoraimc.ui.theme.TextOnPrimary
import com.example.calculadoraimc.ui.theme.TextPrimary
import com.example.calculadoraimc.ui.theme.TextSecondary

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelpScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Como funciona",
                        color = TextOnPrimary,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack,
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = Color.White.copy(alpha = 0.15f)
                        )
                    ) {
                        Icon(
                            Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = "Voltar",
                            tint = TextOnPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
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
                            GradientStart,
                            GradientEnd,
                            BackgroundLight
                        ),
                        startY = 0f,
                        endY = 400f
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(8.dp))

                HelpCard(
                    icon = Icons.Rounded.Calculate,
                    iconColor = Color(0xFF7C3AED),
                    title = "Índice de Massa Corporal (IMC)",
                    formula = "IMC = Peso (kg) ÷ Altura² (m)",
                    description = "O IMC é uma medida internacional usada para avaliar se uma pessoa está no seu peso ideal. Ele relaciona o peso e a altura para classificar o estado nutricional."
                )

                Spacer(modifier = Modifier.height(16.dp))

                HelpCard(
                    icon = Icons.Rounded.LocalFireDepartment,
                    iconColor = Color(0xFFEF4444),
                    title = "Taxa Metabólica Basal (TMB)",
                    formula = "Fórmula de Mifflin-St Jeor",
                    description = "A TMB representa o número de calorias que seu corpo queima em repouso durante um dia.\n\n♂ Homens: (10 × peso) + (6.25 × altura) - (5 × idade) + 5\n\n♀ Mulheres: (10 × peso) + (6.25 × altura) - (5 × idade) - 161"
                )

                Spacer(modifier = Modifier.height(16.dp))

                HelpCard(
                    icon = Icons.Rounded.Balance,
                    iconColor = Color(0xFF10B981),
                    title = "Peso Ideal",
                    formula = "Fórmula de Devine",
                    description = "Uma estimativa de uma faixa de peso saudável com base na altura e no sexo biológico.\n\n♂ Homens: 50 kg + 2.3 kg por polegada acima de 5 pés\n\n♀ Mulheres: 45.5 kg + 2.3 kg por polegada acima de 5 pés"
                )

                Spacer(modifier = Modifier.height(16.dp))

                HelpCard(
                    icon = Icons.Rounded.LocalDining,
                    iconColor = Color(0xFFF59E0B),
                    title = "Necessidade Calórica Diária",
                    formula = "Necessidade = TMB × Fator de Atividade",
                    description = "Estima o total de calorias que você deve consumir por dia para manter seu peso atual, com base no seu nível de atividade física.\n\nOs fatores variam de 1.2 (sedentário) a 1.9 (extremamente ativo)."
                )

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
private fun HelpCard(
    icon: ImageVector,
    iconColor: Color,
    title: String,
    formula: String,
    description: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 12.dp,
                shape = RoundedCornerShape(24.dp),
                ambientColor = Color.Black.copy(alpha = 0.08f)
            ),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            // Header com ícone
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(iconColor.copy(alpha = 0.1f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = iconColor,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Column {
                    Text(
                        text = title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Fórmula em destaque
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFF8FAFC))
                    .padding(12.dp)
            ) {
                Text(
                    text = formula,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = AccentColor
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Descrição
            Text(
                text = description,
                fontSize = 14.sp,
                color = TextSecondary,
                lineHeight = 22.sp
            )
        }
    }
}