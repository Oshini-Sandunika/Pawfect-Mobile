package com.example.pawfect_mobile.ui.screens.register

import com.example.pawfect_mobile.ui.components.Input

data class RegisterState(
    val fullName: Input = Input.Unset,
    val email: Input = Input.Unset,
    val phone: Input = Input.Unset,
    val password: Input = Input.Unset,
    val confirmPassword: Input = Input.Unset,
    val isLoading: Boolean = false,
    val registerError: String? = null,
    val registerSuccess: Boolean = false
)
