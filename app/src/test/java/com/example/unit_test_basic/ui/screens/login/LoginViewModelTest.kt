package com.example.unit_test_basic.ui.screens.login

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class LoginViewModelTest {

    private lateinit var viewModel: LoginViewModel

    @Before
    fun setUp() {
        viewModel = LoginViewModel()
    }

    @Test
    fun `validate returns false when email and password are empty`() {
        val result = viewModel.validate()
        
        assertFalse(result)
        assertEquals("Email tidak boleh kosong", viewModel.emailError)
        assertEquals("Password tidak boleh kosong", viewModel.passwordError)
    }

    @Test
    fun `validate returns false when email format is invalid`() {
        viewModel.onEmailChange("invalid-email")
        viewModel.onPasswordChange("password123")
        
        val result = viewModel.validate()
        
        assertFalse(result)
        assertEquals("Format email tidak valid", viewModel.emailError)
        assertNull(viewModel.passwordError)
    }

    @Test
    fun `validate returns false when password is too short`() {
        viewModel.onEmailChange("test@example.com")
        viewModel.onPasswordChange("12345")
        
        val result = viewModel.validate()
        
        assertFalse(result)
        assertNull(viewModel.emailError)
        assertEquals("Password minimal 6 karakter", viewModel.passwordError)
    }

    @Test
    fun `validate returns true when email and password are valid`() {
        viewModel.onEmailChange("user@example.com")
        viewModel.onPasswordChange("password123")
        
        val result = viewModel.validate()
        
        assertTrue(result)
        assertNull(viewModel.emailError)
        assertNull(viewModel.passwordError)
    }

    @Test
    fun `onEmailChange clears email error`() {
        viewModel.emailError = "Some error"
        
        viewModel.onEmailChange("new@email.com")
        
        assertNull(viewModel.emailError)
        assertEquals("new@email.com", viewModel.email)
    }

    @Test
    fun `onPasswordChange clears password error`() {
        viewModel.passwordError = "Some error"
        
        viewModel.onPasswordChange("newpassword")
        
        assertNull(viewModel.passwordError)
        assertEquals("newpassword", viewModel.password)
    }
}
