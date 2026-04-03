package com.example.unit_test_basic

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.unit_test_basic.ui.screens.login.LoginScreen
import org.junit.Rule
import org.junit.Test

class LoginUiTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun loginScreen_initialState_showsWelcomeMessage() {
        composeTestRule.setContent {
            LoginScreen(onLoginSuccess = {}, onRegisterClick = {})
        }

        composeTestRule.onNodeWithText("Selamat Datang").assertIsDisplayed()
        composeTestRule.onNodeWithText("Silakan masuk ke akun Anda").assertIsDisplayed()
    }

    @Test
    fun loginScreen_emptyFields_showsErrorMessages() {
        composeTestRule.setContent {
            LoginScreen(onLoginSuccess = {}, onRegisterClick = {})
        }

        // Klik tombol login tanpa isi data
        composeTestRule.onNodeWithText("Login").performClick()

        // Pastikan pesan error muncul
        composeTestRule.onNodeWithText("Email tidak boleh kosong").assertIsDisplayed()
        composeTestRule.onNodeWithText("Password tidak boleh kosong").assertIsDisplayed()
    }

    @Test
    fun loginScreen_invalidEmail_showsEmailError() {
        composeTestRule.setContent {
            LoginScreen(onLoginSuccess = {}, onRegisterClick = {})
        }

        // Isi email salah
        composeTestRule.onNodeWithText("Email").performTextInput("email-salah")
        composeTestRule.onNodeWithText("Password").performTextInput("password123")
        
        composeTestRule.onNodeWithText("Login").performClick()

        // Pastikan pesan error format muncul
        composeTestRule.onNodeWithText("Format email tidak valid").assertIsDisplayed()
    }

    @Test
    fun loginScreen_validCredentials_triggersSuccessCallback() {
        var loggedInEmail = ""
        composeTestRule.setContent {
            LoginScreen(
                onLoginSuccess = { loggedInEmail = it },
                onRegisterClick = {}
            )
        }

        // Isi data valid
        composeTestRule.onNodeWithText("Email").performTextInput("user@example.com")
        composeTestRule.onNodeWithText("Password").performTextInput("password123")
        
        composeTestRule.onNodeWithText("Login").performClick()

        // Verifikasi callback terpanggil dengan email yang benar
        assert(loggedInEmail == "user@example.com")
    }
}
