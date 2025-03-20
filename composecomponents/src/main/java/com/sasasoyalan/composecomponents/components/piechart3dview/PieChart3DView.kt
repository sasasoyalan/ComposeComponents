package com.sasasoyalan.composecomponents.components.piechart3dview

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin


data class PieChart3DViewData(
    val label: String,
    val value: Float,
    val color: Color
)

@Composable
fun PieChart3DView(
    data: List<PieChart3DViewData>,
    pieSize: Dp = 250.dp,
    modifier: Modifier = Modifier,
    onSliceSelected: ((PieChart3DViewData?) -> Unit)? = null
) {
    val total = data.sumOf { it.value.toDouble() }.toFloat()
    var selectedSlice by remember { mutableStateOf<PieChart3DViewData?>(null) }
    var angleMap by remember { mutableStateOf(emptyMap<PieChart3DViewData, Pair<Float, Float>>()) }

    LaunchedEffect(data) {
        var startAngle = 0f
        val newAngleMap = mutableMapOf<PieChart3DViewData, Pair<Float, Float>>()
        data.forEach { slice ->
            val sweepAngle = 360f * (slice.value / total)
            newAngleMap[slice] = startAngle to (startAngle + sweepAngle)
            startAngle += sweepAngle
        }
        angleMap = newAngleMap
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Canvas(
            modifier = Modifier
                .size(pieSize)
                .pointerInput(Unit) {
                    detectTapGestures { offset ->
                        val center = Offset(size.width / 2f, size.height / 2f)
                        val dx = offset.x - center.x
                        val dy = offset.y - center.y

                        var clickedAngle = (atan2(dy, dx) * (180 / PI)).toFloat()
                        clickedAngle = (clickedAngle + 360) % 360

                        angleMap.forEach { (slice, angles) ->
                            val start = angles.first
                            val end = angles.second
                            if (clickedAngle in start..end) {
                                selectedSlice = if (selectedSlice == slice) null else slice
                                onSliceSelected?.invoke(selectedSlice)
                            }
                        }
                    }
                }
        ) {
            val center = Offset(size.width / 2, size.height / 2)
            val radius = size.minDimension / 2.5f
            val depth = 25f

            angleMap.forEach { (slice, angles) ->
                val isSelected = slice == selectedSlice
                val shiftDistance = if (isSelected) 30f else 0f
                val angleInRad = Math.toRadians(((angles.first + angles.second) / 2).toDouble())
                val shiftX = cos(angleInRad).toFloat() * shiftDistance
                val shiftY = sin(angleInRad).toFloat() * shiftDistance

                val topLeft = Offset(center.x - radius + shiftX, center.y - radius + shiftY)
                val size = Size(radius * 2, radius * 2)

                drawArc(
                    color = slice.color.copy(alpha = 0.3f),
                    startAngle = angles.first,
                    sweepAngle = angles.second - angles.first,
                    useCenter = true,
                    topLeft = Offset(topLeft.x, topLeft.y + depth),
                    size = size
                )

                val midAngle = (angles.first + angles.second) / 2
                if (midAngle in 90f..270f) { // Sadece sol taraf gölgeli olacak
                    drawArc(
                        color = slice.color.copy(alpha = 0.4f),
                        startAngle = angles.first,
                        sweepAngle = angles.second - angles.first,
                        useCenter = false,
                        topLeft = Offset(topLeft.x, topLeft.y + depth),
                        size = size
                    )
                }

                drawArc(
                    color = slice.color,
                    startAngle = angles.first,
                    sweepAngle = angles.second - angles.first,
                    useCenter = true,
                    topLeft = topLeft,
                    size = size
                )

                val percentage = ((slice.value / total) * 100).toInt()

                val textColor = if (slice.color.luminance() > 0.6f) Color.Black else Color.White

                val textAngleRad = Math.toRadians(((angles.first + angles.second) / 2).toDouble())
                val textX = (center.x + (radius / 2) * cos(textAngleRad)).toFloat()
                val textY = (center.y + (radius / 2) * sin(textAngleRad)).toFloat()

                drawContext.canvas.nativeCanvas.drawText(
                    "$percentage%",
                    textX,
                    textY,
                    android.graphics.Paint().apply {
                        color = if (textColor == Color.Black) android.graphics.Color.BLACK else android.graphics.Color.WHITE
                        textSize = 32f
                        textAlign = android.graphics.Paint.Align.CENTER
                    }
                )
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            data.forEach { slice ->
                var backgroundColor = slice.color
                var fontWeight = FontWeight.Normal
                var size = DpSize(32.dp,14.dp)
                var textSize = 14.sp
                if(slice == selectedSlice) {
                    backgroundColor = adjustBrightnessRGB(backgroundColor, 2.5f)
                    fontWeight = FontWeight.Bold
                    size = DpSize(40.dp,15.dp)
                    textSize = 15.sp
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable {
                            selectedSlice = slice
                            onSliceSelected?.invoke(selectedSlice)
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(size)
                            .clip(RoundedCornerShape(6.dp))
                            .background(backgroundColor)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = slice.label,
                        fontSize = textSize,
                        color = Color.Black,
                        maxLines = 1,
                        fontWeight = fontWeight,
                        fontFamily = FontFamily.Serif
                    )
                }
            }
        }
    }
}

fun adjustBrightnessRGB(color: Color, factor: Float): Color {
    val red = (color.red * factor).coerceIn(0f, 1f)
    val green = (color.green * factor).coerceIn(0f, 1f)
    val blue = (color.blue * factor).coerceIn(0f, 1f)

    return Color(red, green, blue, color.alpha)
}






