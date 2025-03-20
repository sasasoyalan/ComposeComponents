package com.sasasoyalan.composecomponents.components.ratingview

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.Dp

/**
 * Created by Sacid Soyalan
 */
@Composable
fun RatingView(
    maxStars: Int = 5,
    rating: Int = 0,
    starSize: Dp = 32.dp,
    activeColor: Color = Color(0xFFFFC107), // Altın Sarısı
    inactiveColor: Color = Color.Gray,
    onRatingChange: (Int) -> Unit
) {
    var selectedRating by remember { mutableIntStateOf(rating) }

    Row {
        for (i in 1..maxStars) {
            Icon(
                imageVector = if (i <= selectedRating) Icons.Filled.Star else Icons.Outlined.Star,
                contentDescription = "$i Stars",
                tint = if (i <= selectedRating) activeColor else inactiveColor,
                modifier = Modifier
                    .clickable {
                        selectedRating = if (i == selectedRating) 0 else i
                        onRatingChange(selectedRating)
                    }.size(starSize)
            )
        }
    }
}