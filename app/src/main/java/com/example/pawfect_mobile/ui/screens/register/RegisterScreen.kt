package com.example.pawfect_mobile.ui.screens.register

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pawfect_mobile.ui.components.InputType
import com.example.pawfect_mobile.ui.components.TextInput
import com.example.pawfect_mobile.ui.layouts.LoginLayout

@Composable
fun RegisterScreen(
    onNavigateToLogin: () -> Unit = {},
    onRegisterSuccess: () -> Unit = {},
    viewModel: RegisterViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.registerSuccess) {
        if (state.registerSuccess) {
            onRegisterSuccess()
        }
    }

    LoginLayout(
        title = "Create an Account",
        subtitle = "Join Pawfect to find your furry friend"
    ) {
        TextInput(
            label = "Full Name",
            value = state.fullName,
            onValueChange = viewModel::updateFullName,
            inputType = InputType.TEXT,
            required = true,
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextInput(
            label = "Email",
            value = state.email,
            onValueChange = viewModel::updateEmail,
            inputType = InputType.EMAIL,
            required = true,
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextInput(
            label = "Phone",
            value = state.phone,
            onValueChange = viewModel::updatePhone,
            inputType = InputType.PHONE,
            required = true,
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextInput(
            label = "Password",
            value = state.password,
            onValueChange = viewModel::updatePassword,
            inputType = InputType.PASSWORD,
            required = true,
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextInput(
            label = "Confirm Password",
            value = state.confirmPassword,
            onValueChange = viewModel::updateConfirmPassword,
            inputType = InputType.PASSWORD,
            required = true,
            validator = {
                if (it.isBlank()) "Please confirm your password"
                else if (it != state.password.string()) "Passwords do not match"
                else null
            }
        )

        if (state.registerError != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(state.registerError!!, color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = viewModel::register,
            modifier = Modifier.fillMaxWidth(),
            enabled = !state.isLoading &&
                    state.password.valid
                    && state.fullName.valid
                    && state.confirmPassword.valid
                    && state.phone.valid
                    && state.email.valid
        ) {
            Text(if (state.isLoading) "Registering..." else "Register")
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = onNavigateToLogin) {
            Text("Already have an account? Login")
        }
    }
}
