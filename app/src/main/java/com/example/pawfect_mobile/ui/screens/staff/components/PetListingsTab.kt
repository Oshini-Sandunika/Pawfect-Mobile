package com.example.pawfect_mobile.ui.screens.staff.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pawfect_mobile.data.models.Pet
import com.example.pawfect_mobile.ui.components.PetCard
import com.example.pawfect_mobile.ui.components.StyledIconButton

@Composable
fun PetListingsTab(
    pets: List<Pet>,
    onAddPet: () -> Unit,
    onEditPet: (Pet) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            StyledIconButton(
                onClick = onAddPet,
                icon = Icons.Default.Add,
                elevation = 24.dp
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (pets.isEmpty()) {
                item { NoPetsListed() }
            }

            items(pets) { pet ->
                PetCard(pet = pet, onPetClick = { onEditPet(pet) })
            }
        }
    }
}
