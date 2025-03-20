package com.sasasoyalan.composecomponents.components.authview

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.kullanici.composecomponents.auth.AuthView

@Preview(showBackground = true)
@Composable
fun AuthViewPreview() {
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