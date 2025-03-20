package com.sasasoyalan.composecomponents

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sasasoyalan.composecomponents.components.authview.AuthViewPreview

/**
 * Created by Sacid Soyalan
 */
data class ComponentItem(
    val name: String,
    val view: @Composable () -> Unit
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComponentsPreview() {
    var searchText by remember { mutableStateOf("") }
    var selectedComponent by remember { mutableStateOf<ComponentItem?>(null) }

    val components = listOf(
        ComponentItem("AuthView", { AuthViewPreview() })
    )

    val filteredComponents = components.filter {
        it.name.contains(searchText, ignoreCase = true)
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        Text("Komponent Listesi", fontSize = 24.sp, modifier = Modifier.padding(bottom = 8.dp))

        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            label = { Text("Komponent ara..") },
            singleLine = true,
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search Icon") },
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        filteredComponents.forEach { component ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clickable { selectedComponent = component },
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF3F3F3)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = component.name,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(12.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (selectedComponent != null) {
            val sheetState = rememberModalBottomSheetState(
                confirmValueChange = { it != SheetValue.Hidden },
                skipPartiallyExpanded = true
            )

            ModalBottomSheet(
                onDismissRequest = { },
                sheetState = sheetState,
                dragHandle = {
                    Card(
                        shape = RoundedCornerShape(50),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow),
                        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp),
                        modifier = Modifier.padding(12.dp)
                    ) {
                        IconButton(
                            onClick = { selectedComponent = null },
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                modifier = Modifier.size(30.dp),
                                contentDescription = "Kapat",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                },
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                containerColor = Color.White,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = selectedComponent!!.name,
                        fontSize = 22.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    selectedComponent!!.view()
                }
            }
        }

    }
}