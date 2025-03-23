package com.sasasoyalan.composecomponents.componentpreviews

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sasasoyalan.composecomponents.components.expandableview.ExpandableListView

@Preview(showBackground = true)
@Composable
fun ExpandableListViewPreview() {
    val sampleList = listOf("Apple", "Banana", "Orange", "Grape")

    ExpandableListView(
        title = "Fruits",
        itemList = sampleList,
        itemContent = { fruit ->
            Text(
                text = fruit,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .background(Color.White)
                    .padding(horizontal = 16.dp),
                color = Color.DarkGray
            )
        }
    )
}