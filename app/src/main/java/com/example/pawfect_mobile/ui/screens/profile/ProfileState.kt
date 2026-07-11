package com.example.pawfect_mobile.ui.screens.profile

import com.example.pawfect_mobile.ui.components.Input

data class ProfileState(
    val fullName: Input = Input.Unset,
    val phone: Input = Input.Unset,
    val newPassword: Input = Input.Unset,
    val confirmPassword: Input = Input.Unset,
    val isLoading: Boolean = false,
    val showDeleteConfirmDialog: Boolean = false,
    val error: String? = null,
    val accountSuccessMessage: String? = null,
    val passwordSuccessMessage: String? = null
)
