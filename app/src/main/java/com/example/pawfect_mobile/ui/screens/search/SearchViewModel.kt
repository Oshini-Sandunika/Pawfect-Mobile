package com.example.pawfect_mobile.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pawfect_mobile.data.PetService
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.logging.Logger
import kotlin.time.Duration.Companion.milliseconds

class SearchViewModel : ViewModel() {
    private val _state = MutableStateFlow(SearchState())
    val state: StateFlow<SearchState> = _state.asStateFlow()

    private var searchJob: Job? = null


    fun onQueryChange(query: String) {
        _state.update { it.copy(query = query, isLoading = true, hasSearched = true) }
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500.milliseconds) // debounce
            performSearch()
        }
    }

    fun onTypeSelected(type: String) {
        _state.update { it.copy(selectedType = type) }
        performSearch()
    }

    private fun performSearch() {
        val currentState = _state.value
        if (currentState.query.isBlank() && currentState.selectedType == "All") {
            _state.update { it.copy(hasSearched = false, results = emptyList(), error = null, isLoading = false) }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null, hasSearched = true) }
            try {
                val currentState = _state.value
                val results = PetService.searchPets(currentState.query, currentState.selectedType)
                Logger.getLogger("AA").info("Results ${results.size}")
                _state.update { it.copy(isLoading = false, results = results) }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = e.localizedMessage ?: "Failed to search pets"
                    )
                }
            }
        }
    }
}
