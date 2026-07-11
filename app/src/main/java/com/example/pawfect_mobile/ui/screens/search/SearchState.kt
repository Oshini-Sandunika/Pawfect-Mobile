package com.example.pawfect_mobile.ui.screens.search

import com.example.pawfect_mobile.data.models.Pet

data class SearchState(
    val query: String = "",
    val selectedType: String = "All",
    val isLoading: Boolean = false,
    val hasSearched: Boolean = false,
    val results: List<Pet> = emptyList(),
    val error: String? = null
)
