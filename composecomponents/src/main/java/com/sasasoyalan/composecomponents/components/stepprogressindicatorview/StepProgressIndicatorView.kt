package com.sasasoyalan.composecomponents.components.stepprogressindicatorview

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Created by Sacid Soyalan
 */
@Composable
fun StepProgressIndicatorView(
    stepContents: List<@Composable () -> Unit>,
    nextButton: @Composable () -> Unit,
    backButton: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    stepSize: Dp = 24.dp,
    stepColor: Color = Color(0xFF6200EE),
    inactiveColor: Color = Color.LightGray,
    spacing: Dp = 16.dp,
    stepIndicatorType: StepIndicatorType = StepIndicatorType.NumberStep,
    onStepChanged: ((Int) -> Unit)? = null,
    isStepsFinished: () -> Unit
) {
    var currentStepIndex by remember { mutableIntStateOf(0) }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(spacing),
            modifier = Modifier.padding(16.dp)
        ) {
            if (stepIndicatorType == StepIndicatorType.ProgressBarStep) {
                StepIndicator(
                    stepIndicatorType = stepIndicatorType,
                    index = currentStepIndex,
                    isActive = true,
                    isCompleted = false,
                    stepSize = stepSize,
                    stepColor = stepColor,
                    inactiveColor = inactiveColor,
                    stepContentsSize = stepContents.size
                )
            } else {
                stepContents.forEachIndexed { index, _ ->
                    val isActive = index == currentStepIndex
                    val isCompleted = index < currentStepIndex
                    StepIndicator(
                        stepIndicatorType = stepIndicatorType,
                        index = index,
                        isActive = isActive,
                        isCompleted = isCompleted,
                        stepSize = stepSize,
                        stepColor = stepColor,
                        inactiveColor = inactiveColor,
                        stepContentsSize = stepContents.size
                    )
                    if (index < stepContents.size - 1) {
                        Spacer(
                            modifier = Modifier
                                .width(spacing)
                                .height(2.dp)
                                .background(if (index < currentStepIndex) stepColor else inactiveColor)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        val rememberedContents = remember(stepContents) {
            stepContents.map { content -> mutableStateOf(content) }
        }

        Box(modifier = Modifier.fillMaxWidth()) {
            rememberedContents.getOrNull(currentStepIndex)?.value?.invoke()
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (currentStepIndex > 0) {
                Box(modifier = Modifier.clickable {
                    currentStepIndex--
                    onStepChanged?.invoke(currentStepIndex + 1)
                }) {
                    backButton()
                }
            } else {
                Spacer(modifier = Modifier.width(1.dp))
            }

            Box(modifier = Modifier.clickable {
                if (currentStepIndex < stepContents.lastIndex) {
                    currentStepIndex++
                    onStepChanged?.invoke(currentStepIndex + 1)
                } else {
                    isStepsFinished()
                }
            }) {
                nextButton()
            }
        }
    }
}
