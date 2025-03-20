package com.sasasoyalan.composecomponents.components.commentinputview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CommentInputView(
    minChars: Int = 3,
    buttonText: String = "Gönder",
    hint: String = "Yorumunuzu buraya yazın...",
    buttonColor: Color = Color(0xFF6200EE),
    deleteIconColor: Color = Color.Red,
    onCommentSubmit: (String) -> Unit
) {
    var comment by remember { mutableStateOf(TextFieldValue("") ) }
    var isButtonEnabled by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F8F8)),
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        ) {
            Box(modifier = Modifier.padding(12.dp)) {
                if (comment.text.isEmpty()) {
                    Text(text = hint, color = Color.Gray, fontSize = 14.sp)
                }
                BasicTextField(
                    value = comment,
                    onValueChange = {
                        comment = it
                        isButtonEnabled = it.text.length >= minChars
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(72.dp),
                    textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Start),
                    singleLine = false
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { onCommentSubmit(comment.text) },
                enabled = isButtonEnabled,
                colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
            ) {
                Text(buttonText)
            }

            IconButton(onClick = { comment = TextFieldValue("") }) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Sil", tint = deleteIconColor)
            }
        }
    }
}
