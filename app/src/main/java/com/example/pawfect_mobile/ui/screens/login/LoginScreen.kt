package com.example.pawfect_mobile.ui.screens.login

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
import com.example.pawfect_mobile.R
import com.example.pawfect_mobile.ui.components.InputType
import com.example.pawfect_mobile.ui.components.TextInput
import com.example.pawfect_mobile.ui.layouts.LoginLayout

@Composable
fun LoginScreen(
    onNavigateToRegister: () -> Unit = {},
    onLoginSuccess: () -> Unit = {},
    viewModel: LoginViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.loginSuccess) {
        if (state.loginSuccess) {
            onLoginSuccess()
        }
    }

    LoginLayout(
        title = "Welcome back!",
        subtitle = "Enter your login details to continue",
        image = R.drawable.login,
    ) {
        TextInput(
            label = "Email",
            value = state.email,
            onValueChange = viewModel::updateEmail,
            inputType = InputType.EMAIL,
            required = true,
            enabled = !state.isLoading
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextInput(
            label = "Password",
            value = state.password,
            onValueChange = viewModel::updatePassword,
            inputType = InputType.PASSWORD,
            required = true,
            enabled = !state.isLoading
        )

        if (state.loginError != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(state.loginError!!, color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = viewModel::login,
            modifier = Modifier.fillMaxWidth(),
            enabled = !state.isLoading && state.email.valid && state.password.valid
        ) {
            Text(if (state.isLoading) "Logging in..." else "Login")
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = onNavigateToRegister) {
            Text("Don't have an account? Register")
        }
    }
}
