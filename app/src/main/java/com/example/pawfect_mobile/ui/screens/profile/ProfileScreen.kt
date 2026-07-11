package com.example.pawfect_mobile.ui.screens.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pawfect_mobile.ui.components.StyledCard
import com.example.pawfect_mobile.ui.layouts.AppLayout
import com.example.pawfect_mobile.ui.screens.profile.components.AccountDetailsSection
import com.example.pawfect_mobile.ui.screens.profile.components.DangerZoneSection
import com.example.pawfect_mobile.ui.screens.profile.components.DeleteAccountDialog
import com.example.pawfect_mobile.ui.screens.profile.components.SecuritySection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = viewModel(),
    onNavigateBack: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    if (state.showDeleteConfirmDialog) {
        DeleteAccountDialog(
            onConfirm = viewModel::deleteAccount,
            onDismiss = viewModel::dismissDeleteDialog
        )
    }

    AppLayout(
        topBar = {
            TopAppBar(
                title = { Text("Profile") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            StyledCard {
                AccountDetailsSection(
                    fullName = state.fullName,
                    phone = state.phone,
                    isLoading = state.isLoading,
                    onFullNameChange = viewModel::onFullNameChange,
                    onPhoneChange = viewModel::onPhoneChange,
                    onUpdateClick = viewModel::updateProfile,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
                )

                if (state.accountSuccessMessage != null) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = state.accountSuccessMessage!!, color = Color(0xFF4CAF50))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            StyledCard {
                SecuritySection(
                    newPassword = state.newPassword,
                    confirmPassword = state.confirmPassword,
                    isLoading = state.isLoading,
                    onPasswordChange = viewModel::onPasswordChange,
                    onConfirmPasswordChange = viewModel::onConfirmPasswordChange,
                    onUpdateClick = viewModel::updatePassword,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
                )

                if (state.passwordSuccessMessage != null) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = state.passwordSuccessMessage!!, color = Color(0xFF4CAF50))
                }
            }

            if (state.isLoading) {
                Spacer(modifier = Modifier.height(16.dp))
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }

            if (state.error != null) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = state.error!!, color = MaterialTheme.colorScheme.error)
            }



            Spacer(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.height(16.dp))

            StyledCard(
                colors = CardDefaults.cardColors(containerColor = Color.Red.copy(alpha = 0.1f)),
                elevation = CardDefaults.cardElevation()
            ) {
                DangerZoneSection(
                    onLogoutClick = viewModel::logout,
                    onDeleteAccountClick = viewModel::showDeleteDialog,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
                )
            }
        }
    }
}
