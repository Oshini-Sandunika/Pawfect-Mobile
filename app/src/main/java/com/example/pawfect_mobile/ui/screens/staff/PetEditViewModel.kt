package com.example.pawfect_mobile.ui.screens.staff

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pawfect_mobile.data.AuthService
import com.example.pawfect_mobile.data.PetService
import com.example.pawfect_mobile.data.StorageService
import com.example.pawfect_mobile.data.models.Pet
import com.example.pawfect_mobile.ui.components.ImageInputValue
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class PetEditState(
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val pet: Pet = Pet(),
    val imageInput: ImageInputValue = ImageInputValue.Empty,
    val isSuccess: Boolean = false,
    val error: String? = null
)

class PetEditViewModel : ViewModel() {
    private val _state = MutableStateFlow(PetEditState())
    val state: StateFlow<PetEditState> = _state.asStateFlow()

    fun loadPet(petId: String?) {
        if (petId == null) {
            val user = AuthService.currentUserFlow.value
            _state.value = _state.value.copy(
                pet = Pet(shelterId = user?.shelterId ?: "")
            )
            return
        }

        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            try {
                val pet = PetService.getPetById(petId)
                if (pet != null) {
                    val initialImageInput = if (pet.imageUrl.isNullOrBlank()) {
                        ImageInputValue.Empty
                    } else {
                        ImageInputValue.Url(pet.imageUrl!!)
                    }
                    _state.value = _state.value.copy(
                        isLoading = false,
                        pet = pet,
                        imageInput = initialImageInput
                    )
                } else {
                    _state.value = _state.value.copy(isLoading = false, error = "Pet not found")
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(isLoading = false, error = e.message)
            }
        }
    }

    fun updateField(
        name: String? = null,
        species: String? = null,
        breed: String? = null,
        age: String? = null,
        description: String? = null,
        adoptionFee: Double? = null,
        monthlyCost: Double? = null
    ) {
        val currentPet = _state.value.pet
        _state.value = _state.value.copy(
            pet = currentPet.copy(
                name = name ?: currentPet.name,
                species = species ?: currentPet.species,
                breed = breed ?: currentPet.breed,
                age = age ?: currentPet.age,
                description = description ?: currentPet.description,
                adoptionFee = adoptionFee ?: currentPet.adoptionFee,
                monthlyCost = monthlyCost ?: currentPet.monthlyCost
            )
        )
    }

    fun updateImageInput(input: ImageInputValue) {
        _state.value = _state.value.copy(imageInput = input)
    }

    fun savePet(context: android.content.Context) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isSaving = true, error = null)
            try {
                var pet = _state.value.pet
                pet.shelterId = AuthService.currentUserFlow.value?.shelterId ?: return@launch

                val isNewPet = pet.id.isEmpty()
                if (isNewPet) {
                    val done = when (val input = _state.value.imageInput) {
                        ImageInputValue.Empty -> true
                        is ImageInputValue.Url -> {
                            pet.imageUrl = input.url
                            true
                        }

                        else -> false
                    }

                    PetService.addPet(pet)
                    if (done) {
                        _state.value = _state.value.copy(isSaving = false, isSuccess = true)
                        return@launch
                    }
                }

                // Handle image upload if local URI
                when (val input = _state.value.imageInput) {
                    is ImageInputValue.LocalUri -> {
                        val url = StorageService.uploadPetImage(
                            context,
                            input.uri,
                            pet.shelterId,
                            pet.id
                        )
                        pet = pet.copy(imageUrl = url)
                    }

                    is ImageInputValue.Url -> {
                        pet = pet.copy(imageUrl = input.url)
                    }

                    is ImageInputValue.Empty -> {
                        pet = pet.copy(imageUrl = null)
                    }
                }

                PetService.updatePet(pet)

                _state.value = _state.value.copy(
                    isSaving = false,
                    isSuccess = true,
                )
            } catch (e: Exception) {
                _state.value =
                    _state.value.copy(isSaving = false, error = e.message ?: "Failed to save pet")
            }
        }
    }

    fun deletePet() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isSaving = true, error = null)
            try {
                val petId = _state.value.pet.id
                if (petId.isNotEmpty()) {
                    PetService.deletePet(petId)
                }
                _state.value = _state.value.copy(isSaving = false, isSuccess = true)
            } catch (e: Exception) {
                _state.value = _state.value.copy(isSaving = false, error = "Failed to delete pet")
            }
        }
    }
}
