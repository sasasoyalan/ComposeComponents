package com.sasasoyalan.composecomponents.components.stepprogressindicatorview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/** Step Indicator UI Types */
sealed class StepIndicatorType {
    data object NumberStep : StepIndicatorType()
    data object CheckedStep : StepIndicatorType()
    data object ProgressBarStep : StepIndicatorType()
    data class DotStep(val dotSize: Dp = 12.dp) : StepIndicatorType()
    data class IconStep(val icons: List<ImageVector>, val iconStepSize: Dp = 28.dp) : StepIndicatorType()
    data class LabelledStep(val labelList: List<String>) : StepIndicatorType()
}

@Composable
fun StepIndicator(
    stepIndicatorType: StepIndicatorType,
    index: Int,
    isActive: Boolean,
    isCompleted: Boolean,
    stepSize: Dp,
    stepColor: Color,
    inactiveColor: Color,
    stepContentsSize: Int
) = when (stepIndicatorType) {
    StepIndicatorType.NumberStep -> {
        StepBox(isActive, isCompleted, stepSize, stepColor, inactiveColor) {
            Text(
                text = "${index + 1}",
                fontSize = 12.sp,
                color = Color.White
            )
        }
    }

    StepIndicatorType.CheckedStep -> {
        StepBox(isActive, isCompleted, stepSize, stepColor, inactiveColor) {
            if (isCompleted) {
                Text("✓", fontSize = 12.sp, color = Color.White)
            } else {
                Text("${index + 1}", fontSize = 12.sp, color = Color.White)
            }
        }
    }

    is StepIndicatorType.DotStep -> {
        StepBox(isActive, isCompleted, stepIndicatorType.dotSize, stepColor, inactiveColor) {}
    }

    is StepIndicatorType.IconStep -> {
        StepBox(isActive, isCompleted, stepIndicatorType.iconStepSize, stepColor, inactiveColor) {
            stepIndicatorType.icons.getOrNull(index)?.let {
                Icon(
                    imageVector = it,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }

    is StepIndicatorType.ProgressBarStep -> {
        val progress = (index + 1).toFloat() / stepContentsSize
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .background(inactiveColor)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(progress)
                    .fillMaxHeight()
                    .background(stepColor)
            )
        }
    }

    is StepIndicatorType.LabelledStep -> Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(
                    if (isCompleted || isActive) stepColor else inactiveColor
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "${index + 1}",
                fontSize = 12.sp,
                color = Color.White
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = stepIndicatorType.labelList.getOrNull(index) ?: "",
            fontSize = 12.sp,
            color = Color.Gray
        )
    }
}

@Composable
fun StepBox(
    isActive: Boolean,
    isCompleted: Boolean,
    stepSize: Dp,
    stepColor: Color,
    inactiveColor: Color,
    itemContent: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .size(stepSize)
            .clip(CircleShape)
            .background(
                when {
                    isCompleted -> stepColor.copy(alpha = 0.5f)
                    isActive -> stepColor
                    else -> inactiveColor
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        itemContent()
    }
}
