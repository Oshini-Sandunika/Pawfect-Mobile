package com.example.pawfect_mobile.ui.screens.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pawfect_mobile.data.AuthService
import com.example.pawfect_mobile.data.dto.RegisterRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    private val _state = MutableStateFlow(RegisterState())
    val state: StateFlow<RegisterState> = _state.asStateFlow()

    fun updateFullName(fullName: String) {
        _state.update { it.copy(fullName = fullName, fullNameError = null, registerError = null) }
    }

    fun updateEmail(email: String) {
        _state.update { it.copy(email = email, emailError = null, registerError = null) }
    }

    fun updatePhone(phone: String) {
        _state.update { it.copy(phone = phone, phoneError = null, registerError = null) }
    }

    fun updatePassword(password: String) {
        _state.update { it.copy(password = password, passwordError = null, registerError = null) }
    }

    fun updateConfirmPassword(confirmPassword: String) {
        _state.update { it.copy(confirmPassword = confirmPassword, confirmPasswordError = null, registerError = null) }
    }

    fun register() {
        val currentState = _state.value
        
        val fullNameError = if (currentState.fullName.isBlank()) "Full Name cannot be empty" else null
        val emailError = if (currentState.email.isBlank()) "Email cannot be empty" else null
        val phoneError = if (currentState.phone.isBlank()) "Phone cannot be empty" else null
        val passwordError = if (currentState.password.isBlank()) "Password cannot be empty" else null
        
        val confirmPasswordError = if (currentState.confirmPassword.isBlank()) "Please confirm your password"
        else if (currentState.confirmPassword != currentState.password) "Passwords do not match"
        else null

        if (fullNameError != null || emailError != null || phoneError != null || 
            passwordError != null || confirmPasswordError != null) {
            _state.update { 
                it.copy(
                    fullNameError = fullNameError,
                    emailError = emailError,
                    phoneError = phoneError,
                    passwordError = passwordError,
                    confirmPasswordError = confirmPasswordError
                ) 
            }
            return
        }

        _state.update { it.copy(isLoading = true, registerError = null) }
        
        viewModelScope.launch {
            try {
                val request = RegisterRequest(
                    email = currentState.email,
                    password = currentState.password,
                    fullName = currentState.fullName,
                    phone = currentState.phone
                )
                AuthService.register(request)
                _state.update { it.copy(isLoading = false, registerSuccess = true) }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, registerError = e.message ?: "Registration failed") }
            }
        }
    }
}
