package com.example.pawfect_mobile.ui.screens.staff

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pawfect_mobile.ui.components.ErrorCard
import com.example.pawfect_mobile.ui.components.StyledTopBar
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (state.error != null) {
                    ErrorCard(error = state.error!!)
                }

                val pet = state.pet

                OutlinedTextField(
                    value = pet.name,
                    onValueChange = { viewModel.updateField(name = it) },
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = pet.species,
                    onValueChange = { viewModel.updateField(species = it) },
                    label = { Text("Species") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = pet.breed,
                    onValueChange = { viewModel.updateField(breed = it) },
                    label = { Text("Breed") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = pet.age,
                    onValueChange = { viewModel.updateField(age = it) },
                    label = { Text("Age") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = pet.adoptionFee.toString(),
                    onValueChange = {
                        viewModel.updateField(
                            adoptionFee = it.toDoubleOrNull() ?: 0.0
                        )
                    },
                    label = { Text("Adoption Fee") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = pet.monthlyCost.toString(),
                    onValueChange = {
                        viewModel.updateField(
                            monthlyCost = it.toDoubleOrNull() ?: 0.0
                        )
                    },
                    label = { Text("Estimated Monthly Cost") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = pet.description,
                    onValueChange = { viewModel.updateField(description = it) },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3
                )

                val context = androidx.compose.ui.platform.LocalContext.current

                com.example.pawfect_mobile.ui.components.ImageInput(
                    value = state.imageInput,
                    onValueChange = { viewModel.updateImageInput(it) },
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = { viewModel.savePet(context) },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !state.isSaving
                ) {
                    Text(if (state.isSaving) "Saving..." else "Save Pet")
                }

                if (petId != null) {
                    Button(
                        onClick = { viewModel.deletePet() },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !state.isSaving,
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                    ) {
                        Text("Delete Pet")
                    }
                }
            }
        }
    }
}
