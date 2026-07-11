package com.example.pawfect_mobile.ui.screens.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pawfect_mobile.ui.components.ErrorCard
import com.example.pawfect_mobile.ui.components.Loading
import com.example.pawfect_mobile.ui.components.PetCard
import com.example.pawfect_mobile.ui.components.StyledTopBar
import com.example.pawfect_mobile.ui.layouts.AppLayout
import com.example.pawfect_mobile.ui.screens.search.components.NoSearch
import com.example.pawfect_mobile.ui.screens.search.components.NotFound
import com.example.pawfect_mobile.ui.screens.search.components.SearchBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel = viewModel(),
    onNavigateBack: () -> Unit,
    onPetClick: (String) -> Unit
) {
    val state by viewModel.state.collectAsState()


    AppLayout(
        noScroll = true,
        topBar = {
            StyledTopBar("Find a Pet", onNavigateBack)
        }
    ) {
        Column() {
            SearchBar(
                state.query,
                viewModel::onQueryChange,
                state.selectedType,
                viewModel::onTypeSelected
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item { Spacer(modifier = Modifier.height(16.dp)) }
                if (state.isLoading) {
                    item { Loading() }
                } else if (!state.hasSearched) {
                    item { NoSearch() }
                } else if (state.error != null) {
                    item { ErrorCard(error = state.error!!) }
                } else if (state.results.isEmpty()) {
                    item { NotFound() }
                } else {
                    items(state.results) { pet ->
                        PetCard(
                            pet = pet,
                            onPetClick = onPetClick,
                        )
                    }
                }
            }
        }
    }
}
