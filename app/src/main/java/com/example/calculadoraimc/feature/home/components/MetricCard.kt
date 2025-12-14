package com.example.calculadoraimc.feature.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculadoraimc.feature.home.model.MetricCardData
import com.example.calculadoraimc.ui.models.useIcon
import com.example.calculadoraimc.ui.theme.AccentColor
import com.example.calculadoraimc.ui.theme.CalculadoraIMCTheme
import com.example.calculadoraimc.ui.theme.GreenColorDark
import com.example.calculadoraimc.ui.theme.TextPrimary
import com.example.calculadoraimc.ui.theme.TextSecondary
import com.example.calculadoraimc.ui.theme.YellowColorDark

@Composable
fun MetricCard(
    modifier: Modifier = Modifier,
    metrics: MetricCardData
) {
    // Calculo do valor a ser mostrado
    val valueCard = when (metrics) {
        is MetricCardData.Height -> (metrics.value / 100)
        is MetricCardData.Weight -> metrics.value
    }

    val iconTint = when (metrics) {
        is MetricCardData.Height -> GreenColorDark
        is MetricCardData.Weight -> YellowColorDark
    }

    Card(
        modifier = modifier
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(20.dp),
                ambientColor = Color.Black.copy(alpha = 0.05f)
            ),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            // Linha superior
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = metrics.title,
                    fontSize = 14.sp,
                    color = TextSecondary
                )

                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(metrics.color),
                    contentAlignment = Alignment.Center
                ) {
                    IconTag(
                        icon = useIcon(metrics.icon),
                        contentDescription = "Ícone ${metrics.title}",
                        circleColor = Color.Transparent,
                        iconColor = iconTint,
                        circleSize = 36.dp,
                        iconSize = 20.dp
                    )
                }
            }

            // Valores
            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = valueCard.toString(),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )

                Spacer(Modifier.width(4.dp))

                Text(
                    text = metrics.unitMeasure,
                    modifier = Modifier.padding(bottom = 6.dp),
                    fontSize = 14.sp,
                    color = TextSecondary
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MetricCardPreview() {
    CalculadoraIMCTheme {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            MetricCard(
                Modifier.weight(1f),
                metrics = MetricCardData.Height(175f),
            )
            MetricCard(
                Modifier.weight(1f),
                metrics = MetricCardData.Weight(75f),
            )
        }
    }
}