package com.example.unit_test_basic

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.unit_test_basic.ui.screens.register.RegisterScreen
import org.junit.Rule
import org.junit.Test

class RegisterUiTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun registerScreen_initialState_showsTitle() {
        composeTestRule.setContent {
            RegisterScreen(onRegisterClick = {}, onBackToLoginClick = {})
        }

        composeTestRule.onNodeWithText("Daftar Akun Baru").assertIsDisplayed()
        composeTestRule.onNodeWithText("Mulai perjalanan Anda bersama kami").assertIsDisplayed()
    }

    @Test
    fun registerScreen_emptyFields_showsErrorMessages() {
        composeTestRule.setContent {
            RegisterScreen(onRegisterClick = {}, onBackToLoginClick = {})
        }

        // Klik tombol daftar tanpa isi data
        composeTestRule.onNodeWithText("Daftar").performClick()

        // Pastikan semua pesan error muncul
        composeTestRule.onNodeWithText("Nama tidak boleh kosong").assertIsDisplayed()
        composeTestRule.onNodeWithText("Email tidak boleh kosong").assertIsDisplayed()
        composeTestRule.onNodeWithText("Password tidak boleh kosong").assertIsDisplayed()
    }

    @Test
    fun registerScreen_invalidEmail_showsEmailError() {
        composeTestRule.setContent {
            RegisterScreen(onRegisterClick = {}, onBackToLoginClick = {})
        }

        // Isi data dengan email salah
        composeTestRule.onNodeWithText("Nama Lengkap").performTextInput("John Doe")
        composeTestRule.onNodeWithText("Email").performTextInput("email-salah")
        composeTestRule.onNodeWithText("Password").performTextInput("password123")
        
        composeTestRule.onNodeWithText("Daftar").performClick()

        // Pastikan pesan error format email muncul
        composeTestRule.onNodeWithText("Format email tidak valid").assertIsDisplayed()
    }

    @Test
    fun registerScreen_shortPassword_showsPasswordError() {
        composeTestRule.setContent {
            RegisterScreen(onRegisterClick = {}, onBackToLoginClick = {})
        }

        // Isi password terlalu pendek
        composeTestRule.onNodeWithText("Nama Lengkap").performTextInput("John Doe")
        composeTestRule.onNodeWithText("Email").performTextInput("john@example.com")
        composeTestRule.onNodeWithText("Password").performTextInput("123")
        
        composeTestRule.onNodeWithText("Daftar").performClick()

        // Pastikan pesan error minimal karakter muncul
        composeTestRule.onNodeWithText("Password minimal 6 karakter").assertIsDisplayed()
    }

    @Test
    fun registerScreen_validData_triggersRegisterCallback() {
        var isRegistered = false
        composeTestRule.setContent {
            RegisterScreen(
                onRegisterClick = { isRegistered = true },
                onBackToLoginClick = {}
            )
        }

        // Isi semua data dengan benar
        composeTestRule.onNodeWithText("Nama Lengkap").performTextInput("John Doe")
        composeTestRule.onNodeWithText("Email").performTextInput("john@example.com")
        composeTestRule.onNodeWithText("Password").performTextInput("password123")
        
        composeTestRule.onNodeWithText("Daftar").performClick()

        // Verifikasi callback sukses terpanggil
        assert(isRegistered)
    }
}
