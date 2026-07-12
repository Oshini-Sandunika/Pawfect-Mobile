package com.example.pawfect_mobile.ui.screens.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.pawfect_mobile.ui.components.Input
import com.example.pawfect_mobile.ui.components.InputType
import com.example.pawfect_mobile.ui.components.TextInput

@Composable
fun AccountDetailsSection(
    fullName: Input,
    phone: Input,
    isLoading: Boolean,
    onFullNameChange: (Input) -> Unit,
    onPhoneChange: (Input) -> Unit,
    onUpdateClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            text = "Account Details",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(16.dp))

        TextInput(
            label = "Full Name",
            value = fullName,
            onValueChange = onFullNameChange,
            inputType = InputType.TEXT,
            required = true,
            enabled = !isLoading
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextInput(
            label = "Phone Number",
            value = phone,
            onValueChange = onPhoneChange,
            inputType = InputType.PHONE,
            required = false,
            enabled = !isLoading
        )
        Spacer(modifier = Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = onUpdateClick,
                shape = RectangleShape,
                enabled = !isLoading
            ) {
                Text("Update Profile")
            }
        }
    }
}
