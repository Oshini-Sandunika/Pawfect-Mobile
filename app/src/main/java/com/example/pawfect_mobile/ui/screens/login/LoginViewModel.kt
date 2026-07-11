package com.example.pawfect_mobile.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pawfect_mobile.data.AuthService
import com.example.pawfect_mobile.ui.components.Input
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    fun updateEmail(email: Input) {
        _state.update { it.copy(email = email, loginError = null) }
    }

    fun updatePassword(password: Input) {
        _state.update { it.copy(password = password, loginError = null) }
    }

    fun login() {
        val currentState = _state.value

        if (currentState.email !is Input.Valid || currentState.password !is Input.Valid) {
            return
        }

        _state.update { it.copy(isLoading = true, loginError = null) }
        viewModelScope.launch {
            try {
                AuthService.authenticate(currentState.email.value, currentState.password.value)
                _state.update { it.copy(isLoading = false, loginSuccess = true) }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        loginError = e.message ?: "Login failed"
                    )
                }
            }
        }
    }
}
