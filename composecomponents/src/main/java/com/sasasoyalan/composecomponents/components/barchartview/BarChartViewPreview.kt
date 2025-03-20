package com.sasasoyalan.composecomponents.components.barchartview

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Preview(showBackground = true)
@Composable
fun BarChartViewPreview() {
    BarChartView(
        data = listOf(
            BarChartData("ahmet", 4f, "A Önemli"),
            BarChartData("b", 27f),
            BarChartData("c", 14f),
            BarChartData("d", 35f),
            BarChartData("e", 50f), BarChartData("fatma", 40f), BarChartData("gixem", 75f,), BarChartData("7", 110f)
        )
    )
}