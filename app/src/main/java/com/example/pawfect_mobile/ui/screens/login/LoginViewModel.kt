package com.example.pawfect_mobile.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pawfect_mobile.data.AuthService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    fun updateEmail(email: String) {
        _state.update { it.copy(email = email, emailError = null, loginError = null) }
    }

    fun updatePassword(password: String) {
        _state.update { it.copy(password = password, passwordError = null, loginError = null) }
    }

    fun login() {
        val currentState = _state.value
        val emailError = if (currentState.email.isBlank()) "Email cannot be empty" else null
        val passwordError = if (currentState.password.isBlank()) "Password cannot be empty" else null

        if (emailError != null || passwordError != null) {
            _state.update { it.copy(emailError = emailError, passwordError = passwordError) }
            return
        }

        _state.update { it.copy(isLoading = true, loginError = null) }
        viewModelScope.launch {
            try {
                AuthService.authenticate(currentState.email, currentState.password)
                _state.update { it.copy(isLoading = false, loginSuccess = true) }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, loginError = e.message ?: "Login failed") }
            }
        }
    }
}
