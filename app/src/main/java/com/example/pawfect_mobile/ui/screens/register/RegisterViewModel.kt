package com.example.pawfect_mobile.ui.screens.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pawfect_mobile.data.AuthService
import com.example.pawfect_mobile.data.dto.RegisterRequest
import com.example.pawfect_mobile.ui.components.Input
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    private val _state = MutableStateFlow(RegisterState())
    val state: StateFlow<RegisterState> = _state.asStateFlow()

    fun updateFullName(fullName: Input) {
        _state.update { it.copy(fullName = fullName, registerError = null) }
    }

    fun updateEmail(email: Input) {
        _state.update { it.copy(email = email, registerError = null) }
    }

    fun updatePhone(phone: Input) {
        _state.update { it.copy(phone = phone, registerError = null) }
    }

    fun updatePassword(password: Input) {
        _state.update { it.copy(password = password, registerError = null) }
    }

    fun updateConfirmPassword(confirmPassword: Input) {
        _state.update { it.copy(confirmPassword = confirmPassword, registerError = null) }
    }

    fun register() {
        val current = _state.value

        if (current.email !is Input.Valid
            || current.fullName !is Input.Valid
            || current.phone !is Input.Valid
            || current.password !is Input.Valid
            || current.confirmPassword !is Input.Valid
        ) {
            return
        }

        _state.update { it.copy(isLoading = true, registerError = null) }

        viewModelScope.launch {
            try {
                val request = RegisterRequest(
                    email = current.email.value,
                    password = current.password.value,
                    fullName = current.fullName.value,
                    phone = current.phone.value
                )
                AuthService.register(request)
                _state.update { it.copy(isLoading = false, registerSuccess = true) }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        registerError = e.message ?: "Registration failed"
                    )
                }
            }
        }
    }
}
