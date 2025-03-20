package com.sasasoyalan.composecomponents.components.barchartview

import android.graphics.Typeface
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class BarChartData(val xLabel: String, val yValue: Float, val tag: String = "")

@Composable
fun BarChartView(
    data: List<BarChartData>,
    heightDp: Dp = 400.dp,
    barColor: Color = Color(0xFF6200EE),
    selectedBarColor: Color = Color.Red,
    xAxisLabel: String = "X Axis",
    yAxisLabel: String = "Y Axis",
    axisColor: Color = Color.Gray,
) {
    val maxValue = data.maxOf { it.yValue }
    var selectedBar by remember { mutableStateOf<BarChartData?>(null) }
    val scope = rememberCoroutineScope()
    var displayJob by remember { mutableStateOf<Job?>(null) }
    val animatedScale = remember { Animatable(0f) }

    val height = if (heightDp <= 250.dp) {
        250.dp
    } else if (heightDp >= 750.dp) {
        750.dp
    } else heightDp

    LaunchedEffect(Unit) {
        animatedScale.animateTo(1f, animationSpec = tween(1000))
    }

    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        selectedBar?.let { bar ->
            val clickedText = "X: ${bar.xLabel}, Y: ${bar.yValue}${if (bar.tag.isNotEmpty()) ", Tag: ${bar.tag}" else ""}"
            Text(
                text = clickedText,
                fontSize = 16.sp,
                color = Color.Black,
                fontFamily = FontFamily.Serif,
                modifier = Modifier.padding(8.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Box(modifier = Modifier.fillMaxWidth().height(height)) {
            Canvas(modifier = Modifier.fillMaxSize().pointerInput(Unit) {
                detectTapGestures { offset ->
                    data.forEachIndexed { index, bar ->
                        val totalBars = data.size
                        val barWidth = size.width / (totalBars * 1.5f)
                        val spacing = size.width / (totalBars * 1.2f)
                        val left = (index * spacing) + 80f
                        val right = left + barWidth
                        if (offset.x in left..right) {
                            selectedBar = bar
                            displayJob?.cancel()
                            displayJob = scope.launch {
                                delay(2000)
                                selectedBar = null
                            }
                        }
                    }
                }
            }) {
                val totalBars = data.size
                val barWidth = size.width / (totalBars * 1.75f)
                val spacing = size.width / (totalBars * 1.2f)
                val scaleFactor = size.height * 0.75f / maxValue

                data.forEach {
                    val yPos = size.height * 0.85f - (it.yValue * scaleFactor)
                    drawLine(
                        color = axisColor.copy(alpha = 0.3f),
                        start = Offset(80f, yPos),
                        end = Offset(size.width, yPos),
                        strokeWidth = 2f,
                        cap = StrokeCap.Round,
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f))
                    )
                }

                drawLine(
                    color = axisColor,
                    start = Offset(80f, size.height * 0.85f),
                    end = Offset(size.width, size.height * 0.85f),
                    strokeWidth = 3f,
                    cap = StrokeCap.Round
                )

                drawLine(
                    color = axisColor,
                    start = Offset(80f, 20f),
                    end = Offset(80f, size.height * 0.85f),
                    strokeWidth = 3f,
                    cap = StrokeCap.Round
                )

                drawContext.canvas.nativeCanvas.drawText(
                    yAxisLabel,
                    80f - yAxisLabel.length/2*20,
                    0f,
                    android.graphics.Paint().apply {
                        color = android.graphics.Color.DKGRAY
                        textSize = 16.sp.toPx()
                        textAlign = android.graphics.Paint.Align.LEFT
                        typeface = Typeface.create(Typeface.SERIF, Typeface.NORMAL)
                    }
                )

                data.forEachIndexed { index, bar ->
                    val left = (index * spacing) + 80f
                    val top = size.height * 0.85f - (bar.yValue * scaleFactor * animatedScale.value)
                    val bottom = size.height * 0.85f

                    drawRoundRect(
                        color = barColor,
                        topLeft = Offset(left + barWidth/4, top),
                        size = Size(barWidth, bottom - top),
                        cornerRadius = CornerRadius(16f, 16f)
                    )

                    if (selectedBar == bar) {
                        drawRoundRect(
                            color = selectedBarColor,
                            topLeft = Offset(left + barWidth/4, top),
                            size = Size(barWidth, bottom - top),
                            cornerRadius = CornerRadius(16f, 16f),
                            style = Stroke(width = 4f)
                        )
                    }

                    drawContext.canvas.nativeCanvas.drawText(
                        bar.xLabel,
                        left + barWidth * 0.75f,
                        (size.height * 0.88f) + (size.height/45f),
                        android.graphics.Paint().apply {
                            color = android.graphics.Color.BLACK
                            textSize = 30f
                            textAlign = android.graphics.Paint.Align.CENTER
                            typeface = Typeface.create(Typeface.SERIF, Typeface.NORMAL)
                        }
                    )
                    drawContext.canvas.nativeCanvas.drawText(
                        bar.yValue.toString(),
                        50f,
                        top + 10,
                        android.graphics.Paint().apply {
                            color = android.graphics.Color.BLACK
                            textSize = 28f
                            textAlign = android.graphics.Paint.Align.RIGHT
                            typeface = Typeface.create(Typeface.SERIF, Typeface.NORMAL)
                        }
                    )
                }
            }
        }

        Text(
            text = xAxisLabel,
            fontSize = 16.sp,
            color = Color.DarkGray,
            fontFamily = FontFamily.Serif,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}
