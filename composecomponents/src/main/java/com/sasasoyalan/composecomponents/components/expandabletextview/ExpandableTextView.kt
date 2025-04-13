package com.sasasoyalan.composecomponents.components.expandabletextview

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Constraints

/**
 * Created by Sacid Soyalan
 */

enum class ExpandableTextViewState(val value: Boolean) {
    EXPANDED(true),
    COLLAPSED(false)
}
@Composable
fun ExpandableTextView(
    text: String,
    modifier: Modifier = Modifier,
    isAnimEnabled: Boolean = true,
    minimizedMaxLines: Int = 3,
    seeMoreText: String = " see more",
    seeLessText: String = " see less",
    textStyle: TextStyle = LocalTextStyle.current,
    seeMoreStyle: SpanStyle = SpanStyle(color = Color.Red, fontWeight = FontWeight.Bold),
    initialExpandedState: ExpandableTextViewState = ExpandableTextViewState.COLLAPSED
) {
    val density = LocalDensity.current
    var isExpanded by remember { mutableStateOf(initialExpandedState.value) }
    var collapsedText by remember { mutableStateOf<AnnotatedString?>(null) }
    val textMeasurer = rememberTextMeasurer()

    fun findMaxSubstring(
        original: String,
        availableWidth: Float,
        extra: String,
        textStyle: TextStyle
    ): String {
        var low = 0
        var high = original.length
        var best = ""
        while (low <= high) {
            val mid = (low + high) / 2
            val candidate = original.substring(0, mid)
            val annotated = AnnotatedString(candidate + extra)
            val result = textMeasurer.measure(
                text = annotated,
                style = textStyle,
                constraints = Constraints(maxWidth = Int.MAX_VALUE)
            )
            val measuredWidth = result.size.width.toFloat()
            if (measuredWidth <= availableWidth) {
                best = candidate
                low = mid + 1
            } else {
                high = mid - 1
            }
        }
        return best
    }

    BoxWithConstraints(
        modifier = if (isAnimEnabled) modifier.animateContentSize(animationSpec = tween(400)) else modifier
    ) {
        val width = with(density) { maxWidth.toPx() }

        LaunchedEffect(text, isExpanded, width) {
            if (isExpanded) return@LaunchedEffect

            val result = textMeasurer.measure(
                text = AnnotatedString(text),
                style = textStyle,
                constraints = Constraints(maxWidth = width.toInt())
            )

            if (result.lineCount > minimizedMaxLines) {
                val firstPart = (0 until minimizedMaxLines - 1).joinToString("") { lineIndex ->
                    val start = result.getLineStart(lineIndex)
                    val end = result.getLineEnd(lineIndex)
                    text.substring(start, end)
                }

                val lastLineStart = result.getLineStart(minimizedMaxLines - 1)
                val lastLineEnd = result.getLineEnd(minimizedMaxLines - 1)
                val lastLineText = text.substring(lastLineStart, lastLineEnd)
                val extraText = "...$seeMoreText"
                val trimmedLastLine = findMaxSubstring(lastLineText, width, extraText, textStyle)

                collapsedText = buildAnnotatedString {
                    append(firstPart)
                    append(trimmedLastLine)
                    append("...")
                    pushStringAnnotation(tag = "see_more", annotation = "see_more")
                    withStyle(seeMoreStyle) {
                        append(seeMoreText)
                    }
                    pop()
                }
            } else {
                collapsedText = AnnotatedString(text)
            }
        }

        val finalText = if (isExpanded) {
            buildAnnotatedString {
                append(text)
                append(" ")
                pushStringAnnotation(tag = "see_less", annotation = "see_less")
                withStyle(seeMoreStyle) {
                    append(seeLessText)
                }
                pop()
            }
        } else {
            collapsedText ?: AnnotatedString(text)
        }

        ClickableText(
            text = finalText,
            modifier = Modifier,
            style = textStyle,
            maxLines = if (isExpanded) Int.MAX_VALUE else minimizedMaxLines,
            overflow = TextOverflow.Clip,
            onClick = { offset ->
                finalText.getStringAnnotations("see_more", offset, offset).firstOrNull()?.let {
                    isExpanded = true
                }
                finalText.getStringAnnotations("see_less", offset, offset).firstOrNull()?.let {
                    isExpanded = false
                }
            }
        )
    }
}










