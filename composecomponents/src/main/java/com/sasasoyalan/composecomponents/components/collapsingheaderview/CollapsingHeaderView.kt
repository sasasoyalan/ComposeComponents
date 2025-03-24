package com.sasasoyalan.composecomponents.components.collapsingheaderview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * 🔥 **Important Note:**
 * @param bodyContent The main scrollable content of the screen.
 * If you use scrollable layouts like `LazyColumn` etc., pass the given `modifier` directly.
 * If you use non-scrollable layouts like `Column`, `Box`, etc., you **must** apply `.verticalScroll(rememberScrollState())` to the given `modifier`.
 *
 * Example usage:
 * ```
 * bodyContent = { modifier, _ ->
 *      LazyColumn(modifier = modifier) { ... }
 * }
 * ```
 * **or**
 * ```
 * bodyContent = { modifier, _ ->
 *      Column(modifier = modifier.verticalScroll(rememberScrollState())) { ... }
 * }
 * ```
 *
 * Created by Sacid Soyalan
 */
@Composable
fun CollapsingHeaderView(
    headerContentExpanded: @Composable () -> Unit,
    headerTitle: String,
    bodyContent: @Composable (modifier: Modifier, unused: Unit) -> Unit,
    modifier: Modifier = Modifier,
    headerBackgroundColor: Color = Color.Red,
    headerTitleSize: TextUnit = 18.sp,
    headerHeight: Dp = 250.dp,
    minHeaderHeight: Dp = 56.dp,
    cornerRadius: Dp = 12.dp,
    showBackButton: Boolean = false,
    initiallyExpanded: Boolean = false,
    backButtonIcon: ImageVector? = null,
    onBackPressed: (() -> Unit)? = null,
) {
    val maxHeaderHeightPx = with(LocalDensity.current) { headerHeight.toPx() }
    val minHeaderHeightPx = with(LocalDensity.current) { minHeaderHeight.toPx() }
    val headerHeightPx = remember { mutableFloatStateOf(if (initiallyExpanded) maxHeaderHeightPx else minHeaderHeightPx) }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                val newHeight = (headerHeightPx.floatValue + delta).coerceIn(minHeaderHeightPx, maxHeaderHeightPx)
                val consumed = newHeight - headerHeightPx.floatValue
                headerHeightPx.floatValue = newHeight
                return Offset(0f, consumed)
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .nestedScroll(nestedScrollConnection)
    ) {
        val progress = ((headerHeightPx.floatValue - minHeaderHeightPx) / (maxHeaderHeightPx - minHeaderHeightPx)).coerceIn(0f, 1f)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(with(LocalDensity.current) { headerHeightPx.floatValue.toDp() })
                .clip(RoundedCornerShape(bottomStart = cornerRadius, bottomEnd = cornerRadius))
                .background(headerBackgroundColor)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer { alpha = progress }
            ) {
                headerContentExpanded()
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer { alpha = 1f - progress },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = headerTitle,
                    color = if (headerBackgroundColor.luminance() > 0.6f) Color.Black else Color.White,
                    fontSize = headerTitleSize,
                    modifier = Modifier.padding(12.dp)
                )
            }

            if (showBackButton) {
                IconButton(
                    onClick = { onBackPressed?.invoke() },
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(12.dp)
                ) {
                    Icon(
                        imageVector = backButtonIcon ?: Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            }
        }

        val mod = Modifier
            .fillMaxSize()
            .padding(top = with(LocalDensity.current) { headerHeightPx.floatValue.toDp() })

        bodyContent(mod, Unit)
    }
}

