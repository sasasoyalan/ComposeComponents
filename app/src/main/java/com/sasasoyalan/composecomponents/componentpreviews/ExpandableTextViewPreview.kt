package com.sasasoyalan.composecomponents.componentpreviews

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sasasoyalan.composecomponents.components.expandabletextview.ExpandableTextView
import com.sasasoyalan.composecomponents.components.expandabletextview.ExpandableTextViewState

/**
 * Created by Sacid Soyalan
 */
@Preview(showBackground = true)
@Composable
fun ExpandableTextViewPreview() {
    ExpandableTextView(
        text = "In Jetpack Compose applications, this structure is very useful for shortening long texts. It keeps the interface clean and prevents overwhelming the user. Moreover, it allows us to present \"read more / read less\" functionality with smooth animations.",
        minimizedMaxLines = 2,
        seeMoreText = "see more",
        seeLessText = "see less",
        initialExpandedState = ExpandableTextViewState.COLLAPSED,
        modifier = Modifier.padding(16.dp)
    )
}