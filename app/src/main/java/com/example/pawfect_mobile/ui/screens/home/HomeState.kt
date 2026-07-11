package com.example.pawfect_mobile.ui.screens.home

import com.example.pawfect_mobile.data.models.Pet
import com.example.pawfect_mobile.data.models.User

data class HomeState(
    val currentUser: User? = null,
    val featuredPets: List<Pet> = emptyList(),
    val isLoadingPets: Boolean = false,
    val petsError: String? = null
)
