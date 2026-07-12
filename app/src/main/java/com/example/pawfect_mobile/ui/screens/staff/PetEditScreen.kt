package com.example.pawfect_mobile.ui.screens.staff

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pawfect_mobile.ui.components.ErrorCard
import com.example.pawfect_mobile.ui.components.ImageInput
import com.example.pawfect_mobile.ui.components.Input
import com.example.pawfect_mobile.ui.components.StyledCard
import com.example.pawfect_mobile.ui.components.StyledTopBar
import com.example.pawfect_mobile.ui.components.TextInput
import com.example.pawfect_mobile.ui.layouts.AppLayout

@Composable
fun PetEditScreen(
    petId: String?,
    onNavigateBack: () -> Unit,
    viewModel: PetEditViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(petId) {
        viewModel.loadPet(petId)
    }

    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) {
            onNavigateBack()
        }
    }

    AppLayout(
        topBar = {
            StyledTopBar(
                title = if (petId == null) "Add Pet" else "Edit Pet",
                goBack = onNavigateBack
            )
        }
    ) {
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        } else {
            StyledCard(modifier = Modifier.padding(12.dp)) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    if (state.error != null) {
                        ErrorCard(error = state.error!!)
                    }

                    val pet = state.pet

                    TextInput(
                        label = "Name",
                        value = Input.Valid(pet.name),
                        onValueChange = { viewModel.updateField(name = it.string()) },
                        enabled = !state.isSaving
                    )
                    TextInput(
                        label = "Species",
                        value = Input.Valid(pet.species),
                        onValueChange = { viewModel.updateField(species = it.string()) },
                        enabled = !state.isSaving
                    )
                    TextInput(
                        label = "Breed",
                        value = Input.Valid(pet.breed),
                        onValueChange = { viewModel.updateField(breed = it.string()) },
                        enabled = !state.isSaving
                    )
                    TextInput(
                        label = "Age",
                        value = Input.Valid(pet.age),
                        onValueChange = { viewModel.updateField(age = it.string()) },
                        enabled = !state.isSaving
                    )
                    TextInput(
                        label = "Adoption Fee",
                        value = Input.Valid(pet.adoptionFee.toString()),
                        onValueChange = {
                            viewModel.updateField(
                                adoptionFee = it.string().toDoubleOrNull() ?: 0.0
                            )
                        },
                        enabled = !state.isSaving
                    )
                    TextInput(
                        label = "Monthly Cost",
                        value = Input.Valid(pet.monthlyCost.toString()),
                        onValueChange = {
                            viewModel.updateField(
                                monthlyCost = it.string().toDoubleOrNull() ?: 0.0
                            )
                        },
                        enabled = !state.isSaving
                    )
                    TextInput(
                        label = "Description",
                        value = Input.Valid(pet.description),
                        lines = 4,
                        onValueChange = { viewModel.updateField(description = it.string()) },
                        enabled = !state.isSaving
                    )

                    val context = androidx.compose.ui.platform.LocalContext.current

                    ImageInput(
                        value = state.imageInput,
                        onValueChange = { viewModel.updateImageInput(it) },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !state.isSaving
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = if (petId != null) {
                            Arrangement.SpaceBetween
                        } else {
                            Arrangement.End
                        }
                    ) {
                        if (petId != null) {
                            Button(
                                onClick = { viewModel.deletePet() },
                                enabled = !state.isSaving,
                                shape = RectangleShape,
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                            ) {
                                Text("Delete Pet")
                            }
                        }

                        Button(
                            onClick = { viewModel.savePet(context) },
                            shape = RectangleShape,
                            enabled = !state.isSaving
                        ) {
                            Text(if (state.isSaving) "Saving..." else "Save Pet")
                        }


                    }
                }
            }
        }
    }
}
