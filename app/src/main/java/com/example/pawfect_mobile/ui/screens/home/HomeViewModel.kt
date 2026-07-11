package com.example.pawfect_mobile.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pawfect_mobile.data.AuthService
import com.example.pawfect_mobile.data.PetService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    init {
        // Observe current user
        viewModelScope.launch {
            AuthService.currentUserFlow.collect { user ->
                _state.update { it.copy(currentUser = user) }
            }
        }
        
        loadFeaturedPets()
    }

    private fun loadFeaturedPets() {
        _state.update { it.copy(isLoadingPets = true, petsError = null) }
        viewModelScope.launch {
            try {
                val pets = PetService.getFeaturedPets()
                _state.update { it.copy(isLoadingPets = false, featuredPets = pets) }
            } catch (e: Exception) {
                _state.update { it.copy(isLoadingPets = false, petsError = e.message ?: "Failed to load featured pets") }
            }
        }
    }
}
