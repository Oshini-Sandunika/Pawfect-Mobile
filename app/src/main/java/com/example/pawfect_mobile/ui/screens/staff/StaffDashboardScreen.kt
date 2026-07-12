package com.example.pawfect_mobile.ui.screens.staff

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pawfect_mobile.ui.components.ErrorCard
import com.example.pawfect_mobile.ui.components.LoadingCard
import com.example.pawfect_mobile.ui.components.StyledTopBar
import com.example.pawfect_mobile.ui.layouts.AppLayout
import com.example.pawfect_mobile.ui.screens.staff.components.InquiriesTab
import com.example.pawfect_mobile.ui.screens.staff.components.PetListingsTab
import com.example.pawfect_mobile.ui.screens.staff.components.ShelterInfoTab

@Composable
fun StaffDashboardScreen(
    viewModel: StaffViewModel = viewModel(),
    onNavigateBack: () -> Unit,
    onNavigateToPetEdit: (String?) -> Unit
) {
    val state by viewModel.state.collectAsState()
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("Pets", "Shelter", "Inquiries")

    androidx.compose.runtime.LaunchedEffect(Unit) {
        viewModel.loadData()
    }

    AppLayout(
        topBar = {
            StyledTopBar(title = "Staff Dashboard", goBack = onNavigateBack)
        },
        noScroll = true
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            TabRow(selectedTabIndex = selectedTabIndex) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = { Text(title) }
                    )
                }
            }

            if (state.isLoading) {
                LoadingCard(modifier = Modifier.padding(16.dp))
            } else if (state.error != null) {
                ErrorCard(error = state.error!!, modifier = Modifier.padding(16.dp))
            } else {
                when (selectedTabIndex) {
                    0 -> PetListingsTab(
                        pets = state.pets,
                        onAddPet = { onNavigateToPetEdit(null) },
                        onEditPet = { onNavigateToPetEdit(it.id) }
                    )

                    1 -> state.shelter?.let { shelter ->
                        ShelterInfoTab(
                            shelter = shelter,
                            onSave = { updatedShelter, context, imageInput ->
                                viewModel.updateShelter(context, updatedShelter, imageInput)
                            }
                        )
                    }

                    2 -> InquiriesTab(inquiries = state.inquiries)
                }
            }
        }
    }
}
