package com.example.pawfect_mobile.ui.screens.login

import com.example.pawfect_mobile.ui.components.Input

data class LoginState(
    val email: Input = Input.Unset,
    val password: Input = Input.Unset,
    val isLoading: Boolean = false,
    val loginError: String? = null,
    val loginSuccess: Boolean = false
)
