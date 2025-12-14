package com.example.calculadoraimc.feature.history.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.calculadoraimc.data.local.entity.IMCHistory
import com.example.calculadoraimc.ui.theme.AccentColor
import com.example.calculadoraimc.ui.theme.AccentLight
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottom
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStart
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.core.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModel
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.core.cartesian.data.LineCartesianLayerModel
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.math.roundToInt

@Composable
fun IMCHistoryChart(historyList: List<IMCHistory>) {
    if (historyList.isEmpty()) return

    val chartData = remember(historyList) { historyList.reversed() }

    val model = remember(chartData) {
        CartesianChartModel(
            LineCartesianLayerModel.build {
                series(chartData.map { it.imc })
            }
        )
    }

    val dateTimeFormatter = remember(chartData) {
        CartesianValueFormatter { _, value, _ ->
            val index = value.toInt()

            if (index in chartData.indices) {
                val date = chartData[index].date
                SimpleDateFormat("dd/MM", Locale.getDefault()).format(date)
            } else {
                ""
            }
        }
    }

    CartesianChartHost(
        chart = rememberCartesianChart(
            rememberLineCartesianLayer(),
            startAxis = VerticalAxis.rememberStart(),
            bottomAxis = HorizontalAxis.rememberBottom(
                valueFormatter = dateTimeFormatter
            ),
        ),
        model = model,
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
    )
}