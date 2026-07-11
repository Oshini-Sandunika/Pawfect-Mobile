package com.example.pawfect_mobile.ui.screens.profile.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.pawfect_mobile.ui.components.Input
import com.example.pawfect_mobile.ui.components.InputType
import com.example.pawfect_mobile.ui.components.TextInput

@Composable
fun SecuritySection(
    newPassword: Input,
    confirmPassword: Input,
    isLoading: Boolean,
    onPasswordChange: (Input) -> Unit,
    onConfirmPasswordChange: (Input) -> Unit,
    onUpdateClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        Text(
            text = "Security",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))

        TextInput(
            label = "New Password",
            value = newPassword,
            onValueChange = onPasswordChange,
            inputType = InputType.PASSWORD,
            required = true
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextInput(
            label = "Confirm Password",
            value = confirmPassword,
            onValueChange = onConfirmPasswordChange,
            inputType = InputType.PASSWORD,
            required = true,
            validator = { input ->
                if (input != newPassword.string()) "Passwords do not match" else null
            }
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onUpdateClick,
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading
        ) {
            Text("Change Password")
        }
    }
}
