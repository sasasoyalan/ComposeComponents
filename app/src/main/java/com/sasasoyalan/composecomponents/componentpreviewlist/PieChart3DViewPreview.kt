package com.sasasoyalan.composecomponents.componentpreviewlist

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.sasasoyalan.composecomponents.components.piechart3dview.PieChart3DView
import com.sasasoyalan.composecomponents.components.piechart3dview.PieChart3DViewData

@Preview(showBackground = true)
@Composable
internal fun PieChart3DViewPreview() {
    PieChart3DView(
        listOf(
            PieChart3DViewData("Bursa", 30f, Color.Blue),
            PieChart3DViewData("Ceyhan", 20f, Color.Green),
            PieChart3DViewData("Denizli", 15f, Color.Yellow),
            PieChart3DViewData("Elazığ", 35f, Color.Cyan)
        )
    )
}