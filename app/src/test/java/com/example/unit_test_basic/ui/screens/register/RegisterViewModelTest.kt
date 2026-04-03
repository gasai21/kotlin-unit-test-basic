package com.example.unit_test_basic.ui.screens.register

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class RegisterViewModelTest {

    private lateinit var viewModel: RegisterViewModel

    @Before
    fun setUp() {
        viewModel = RegisterViewModel()
    }

    @Test
    fun `validate returns false when all fields are empty`() {
        val result = viewModel.validate()

        assertFalse(result)
        assertEquals("Nama tidak boleh kosong", viewModel.nameError)
        assertEquals("Email tidak boleh kosong", viewModel.emailError)
        assertEquals("Password tidak boleh kosong", viewModel.passwordError)
    }

    @Test
    fun `validate returns false when email format is invalid`() {
        viewModel.onNameChange("John Doe")
        viewModel.onEmailChange("invalid-email")
        viewModel.onPasswordChange("password123")

        val result = viewModel.validate()

        assertFalse(result)
        assertNull(viewModel.nameError)
        assertEquals("Format email tidak valid", viewModel.emailError)
        assertNull(viewModel.passwordError)
    }

    @Test
    fun `validate returns false when password is too short`() {
        viewModel.onNameChange("John Doe")
        viewModel.onEmailChange("john@example.com")
        viewModel.onPasswordChange("12345")

        val result = viewModel.validate()

        assertFalse(result)
        assertNull(viewModel.nameError)
        assertNull(viewModel.emailError)
        assertEquals("Password minimal 6 karakter", viewModel.passwordError)
    }

    @Test
    fun `validate returns true when all fields are valid`() {
        viewModel.onNameChange("John Doe")
        viewModel.onEmailChange("john@example.com")
        viewModel.onPasswordChange("password123")

        val result = viewModel.validate()

        assertTrue(result)
        assertNull(viewModel.nameError)
        assertNull(viewModel.emailError)
        assertNull(viewModel.passwordError)
    }

    @Test
    fun `onNameChange clears name error`() {
        viewModel.nameError = "Some error"
        viewModel.onNameChange("John")
        assertNull(viewModel.nameError)
        assertEquals("John", viewModel.name)
    }

    @Test
    fun `onEmailChange clears email error`() {
        viewModel.emailError = "Some error"
        viewModel.onEmailChange("john@example.com")
        assertNull(viewModel.emailError)
        assertEquals("john@example.com", viewModel.email)
    }

    @Test
    fun `onPasswordChange clears password error`() {
        viewModel.passwordError = "Some error"
        viewModel.onPasswordChange("password123")
        assertNull(viewModel.passwordError)
        assertEquals("password123", viewModel.password)
    }
}
