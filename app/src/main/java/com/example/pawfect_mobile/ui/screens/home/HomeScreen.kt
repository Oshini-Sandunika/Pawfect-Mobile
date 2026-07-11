package com.example.pawfect_mobile.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pawfect_mobile.R
import com.example.pawfect_mobile.ui.components.ErrorCard
import com.example.pawfect_mobile.ui.components.LoadingCard
import com.example.pawfect_mobile.ui.components.PetCard
import com.example.pawfect_mobile.ui.layouts.AppLayout
import com.example.pawfect_mobile.ui.screens.home.components.ActionCard
import com.example.pawfect_mobile.ui.screens.home.components.HomeTopBar
import com.example.pawfect_mobile.ui.screens.home.components.ImageCarousel
import com.example.pawfect_mobile.ui.screens.home.components.NoFeatured

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(),
    onProfileClick: () -> Unit,
    onPetClick: (id: String) -> Unit,
    onSearchClick: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    AppLayout(noInset = true) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Hero Section
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(420.dp)
            ) {
                ImageCarousel()

                HomeTopBar(
                    state.currentUser?.fullName, onProfileClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 32.dp)
                        .safeContentPadding()
                )
            }


            // Featured Pets Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (-120).dp)
            ) {
                // Tools Card with Search
                ActionCard(
                    onSearchClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )

                Column(
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {

                    Text(
                        text = stringResource(R.string.featured_pets),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    if (state.isLoadingPets) {
                        LoadingCard()
                    } else if (state.petsError != null) {
                        ErrorCard(error = state.petsError!!)
                    } else if (state.featuredPets.isEmpty()) {
                        NoFeatured()
                    } else {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            state.featuredPets.forEach { pet ->
                                PetCard(pet = pet, onPetClick)
                            }
                        }
                    }
                }

            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
