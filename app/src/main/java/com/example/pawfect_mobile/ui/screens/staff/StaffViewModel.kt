package com.example.pawfect_mobile.ui.screens.staff

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pawfect_mobile.data.AuthService
import com.example.pawfect_mobile.data.InquiryService
import com.example.pawfect_mobile.data.PetService
import com.example.pawfect_mobile.data.ShelterService
import com.example.pawfect_mobile.data.StorageService
import com.example.pawfect_mobile.data.models.Inquiry
import com.example.pawfect_mobile.data.models.Pet
import com.example.pawfect_mobile.data.models.Shelter
import com.example.pawfect_mobile.ui.components.ImageInputValue
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class StaffState(
    val isLoading: Boolean = false,
    val shelter: Shelter? = null,
    val pets: List<Pet> = emptyList(),
    val inquiries: List<Inquiry> = emptyList(),
    val error: String? = null
)

class StaffViewModel : ViewModel() {
    private val _state = MutableStateFlow(StaffState())
    val state: StateFlow<StaffState> = _state.asStateFlow()

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            try {
                val user = AuthService.currentUserFlow.value
                val shelterId = user?.shelterId

                if (shelterId == null) {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = "No shelter associated with this user."
                    )
                    return@launch
                }

                val shelter = ShelterService.getShelterById(shelterId)
                val shelterPets = PetService.getByShelterId(shelterId)

                val inquiries = InquiryService.getInquiriesForShelter(shelterId)

                _state.value = _state.value.copy(
                    isLoading = false,
                    shelter = shelter,
                    pets = shelterPets,
                    inquiries = inquiries
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message ?: "An error occurred."
                )
            }
        }
    }

    fun updateShelter(
        context: android.content.Context,
        updatedShelter: Shelter,
        imageInput: ImageInputValue
    ) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            try {
                var shelterToSave = updatedShelter
                shelterToSave.id = AuthService.currentUserFlow.value?.shelterId ?: return@launch

                when (imageInput) {
                    is ImageInputValue.LocalUri -> {
                        val url = StorageService.uploadShelterImage(
                            context,
                            imageInput.uri,
                            shelterToSave.id
                        )
                        shelterToSave = shelterToSave.copy(logo = url)
                    }

                    is ImageInputValue.Url -> {
                        shelterToSave = shelterToSave.copy(logo = imageInput.url)
                    }

                    is ImageInputValue.Empty -> {
                        shelterToSave = shelterToSave.copy(logo = "")
                    }
                }

                ShelterService.updateShelter(shelterToSave)
                _state.value = _state.value.copy(isLoading = false, shelter = shelterToSave)
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message ?: "Failed to update shelter"
                )
            }
        }
    }
}
