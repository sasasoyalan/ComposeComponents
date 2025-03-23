package com.sasasoyalan.composecomponents.componentpreviewlist

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.sasasoyalan.composecomponents.components.authview.AuthView

/**
 * Created by Sacid Soyalan
 */

@Preview(showBackground = true)
@Composable
internal fun AuthViewPreview() {
    AuthView(
        emailPlaceholder = "E-Posta",
        passwordPlaceholder = "Şifre",
        buttonText = "Giriş Yap",
        textColor = Color.Black,
        buttonColor = Color(0xFF6200EE),
        buttonTextColor = Color.White,
        emailErrorMessage = "Geçersiz e-posta!",
        passwordErrorMessage = "Şifre en az 8 karakter olmalıdır!",
        emailValidation = {  it.contains("@") && it.contains(".") },
        passwordValidation = { it.length >= 8 }
    ) { email, password ->
        println("Butona basıldı: Email=$email, Password=$password")
    }
}