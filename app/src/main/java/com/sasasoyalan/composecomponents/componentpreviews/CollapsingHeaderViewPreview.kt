package com.sasasoyalan.composecomponents.componentpreviews

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sasasoyalan.composecomponents.R
import com.sasasoyalan.composecomponents.components.collapsingheaderview.CollapsingHeaderView

@Preview(showBackground = true)
@Composable
fun CollapsingHeaderViewPreview() {
    CollapsingHeaderView(
        headerTitle = "Header Title",
        headerContentExpanded = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.gorsel),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                Text(
                    text = "Explore Paris",
                    fontSize = 28.sp,
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(start = 16.dp, bottom = 16.dp)
                )
            }
        },
        bodyContent = { modifier, _ ->
            /*LazyColumn(modifier) {
                items(30) { item ->
                    Text(
                        text = "Item #$item",
                        fontSize = 18.sp,
                        modifier = Modifier.padding(vertical = 8.dp))
                }
            } */
            Column {
                Text(
                    text = "Item ",
                    fontSize = 18.sp,
                    modifier = Modifier.padding(vertical = 8.dp))
            }
        },
        showBackButton = true
    )
}