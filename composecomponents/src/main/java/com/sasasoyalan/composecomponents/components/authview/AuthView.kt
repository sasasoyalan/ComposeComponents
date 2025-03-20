package com.sasasoyalan.composecomponents.components.authview

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Created by Sacid Soyalan
 */
@Composable
fun AuthView(
    emailPlaceholder: String,
    passwordPlaceholder: String,
    buttonText: String,
    modifier: Modifier = Modifier,
    textColor: Color = Color.Black,
    buttonColor: Color = Color.Blue,
    buttonTextColor: Color = Color.White,
    emailIcon: ImageVector? = null,
    passwordIcon: ImageVector? = null,
    emailValidation: (String) -> Boolean,
    passwordValidation: (String) -> Boolean,
    emailErrorMessage: String,
    passwordErrorMessage: String,
    onButtonClick: (String, String) -> Unit,
) {
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }

    val isEmailValid = emailValidation(email.text)
    val isPasswordValid = passwordValidation(password.text)
    val isFormValid = isEmailValid && isPasswordValid

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                emailError = if (emailValidation(it.text)) null else emailErrorMessage
            },
            label = { Text(emailPlaceholder, color = textColor) },
            leadingIcon = emailIcon?.let { icon -> { Icon(icon, contentDescription = "Email Icon") } },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            isError = emailError != null,
            textStyle = LocalTextStyle.current.copy(color = textColor),
            modifier = Modifier.fillMaxWidth()
        )
        if (emailError != null) {
            Text(emailError!!, color = Color.Red, fontSize = 12.sp)
        }

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                passwordError = if (passwordValidation(it.text)) null else passwordErrorMessage
            },
            label = { Text(passwordPlaceholder, color = textColor) },
            leadingIcon = passwordIcon?.let { icon -> { Icon(icon, contentDescription = "Password Icon") } },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = PasswordVisualTransformation(),
            isError = passwordError != null,
            textStyle = LocalTextStyle.current.copy(color = textColor),
            modifier = Modifier.fillMaxWidth()
        )
        if (passwordError != null) {
            Text(passwordError!!, color = Color.Red, fontSize = 12.sp)
        }

        Button(
            onClick = { onButtonClick(email.text, password.text) },
            colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
            modifier = Modifier.fillMaxWidth().padding(top = 12.dp).height(48.dp),
            enabled = isFormValid
        ) {
            Text(buttonText, color = buttonTextColor, fontSize = 18.sp)
        }
    }
}
