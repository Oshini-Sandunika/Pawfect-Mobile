package com.example.pawfect_mobile.ui.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pawfect_mobile.data.AuthService
import com.example.pawfect_mobile.ui.components.Input
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    private val _state = MutableStateFlow(ProfileState())
    val state: StateFlow<ProfileState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            AuthService.currentUserFlow.collect { user ->
                if (user != null) {
                    _state.update {
                        it.copy(
                            fullName = Input.Valid(user.fullName),
                            phone = Input.Valid(user.phone)
                        )
                    }
                }
            }
        }
    }

    fun onFullNameChange(input: Input) {
        _state.update {
            it.copy(
                fullName = input,
                error = null,
                accountSuccessMessage = null,
                passwordSuccessMessage = null
            )
        }
    }

    fun onPhoneChange(input: Input) {
        _state.update {
            it.copy(
                phone = input, error = null,
                accountSuccessMessage = null,
                passwordSuccessMessage = null
            )
        }
    }

    fun onPasswordChange(input: Input) {
        _state.update {
            it.copy(
                newPassword = input, error = null,
                accountSuccessMessage = null,
                passwordSuccessMessage = null
            )
        }
    }

    fun onConfirmPasswordChange(input: Input) {
        _state.update {
            it.copy(
                confirmPassword = input, error = null,
                accountSuccessMessage = null,
                passwordSuccessMessage = null
            )
        }
    }

    fun updateProfile() {
        val currentState = _state.value
        val name = currentState.fullName.string()
        val phone = currentState.phone.string()

        if (name.isBlank()) {
            _state.update { it.copy(error = "Full name cannot be empty") }
            return
        }

        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true, error = null,
                    accountSuccessMessage = null,
                    passwordSuccessMessage = null
                )
            }
            try {
                AuthService.updateProfile(name, phone)
                _state.update {
                    it.copy(
                        isLoading = false,
                        accountSuccessMessage = "Profile updated successfully"
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = e.localizedMessage ?: "Failed to update profile"
                    )
                }
            }
        }
    }

    fun updatePassword() {
        val currentState = _state.value
        val password = currentState.newPassword.string()
        val confirm = currentState.confirmPassword.string()

        if (password.length < 6) {
            _state.update { it.copy(error = "Password must be at least 6 characters") }
            return
        }

        if (password != confirm) {
            _state.update { it.copy(error = "Passwords do not match") }
            return
        }

        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true, error = null,
                    accountSuccessMessage = null,
                    passwordSuccessMessage = null
                )
            }
            try {
                AuthService.updatePassword(password)
                _state.update {
                    it.copy(
                        isLoading = false,
                        newPassword = Input.Unset,
                        confirmPassword = Input.Unset,
                        passwordSuccessMessage = "Password updated successfully"
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = e.localizedMessage
                            ?: "Failed to update password. You may need to log in again."
                    )
                }
            }
        }
    }

    fun logout() {
        AuthService.logout()
    }

    fun showDeleteDialog() {
        _state.update { it.copy(showDeleteConfirmDialog = true) }
    }

    fun dismissDeleteDialog() {
        _state.update { it.copy(showDeleteConfirmDialog = false) }
    }

    fun deleteAccount() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true,
                    error = null,
                    passwordSuccessMessage = null,
                    accountSuccessMessage = null,
                    showDeleteConfirmDialog = false
                )
            }
            try {
                AuthService.deleteAccount()
                // Account deleted, the AuthStateListener in AppNavigation will handle navigation
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = e.localizedMessage
                            ?: "Failed to delete account. You may need to log in again."
                    )
                }
            }
        }
    }
}
