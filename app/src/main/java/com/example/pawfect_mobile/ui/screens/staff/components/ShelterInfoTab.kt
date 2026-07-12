package com.example.pawfect_mobile.ui.screens.staff.components

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.pawfect_mobile.data.models.Shelter
import com.example.pawfect_mobile.ui.components.ImageInput
import com.example.pawfect_mobile.ui.components.ImageInputValue

@Composable
fun ShelterInfoTab(
    shelter: Shelter,
    onSave: (Shelter, Context, ImageInputValue) -> Unit
) {
    var name by remember { mutableStateOf(shelter.name) }
    var address by remember { mutableStateOf(shelter.address) }
    var phone by remember { mutableStateOf(shelter.phone) }
    var email by remember { mutableStateOf(shelter.email) }
    var description by remember { mutableStateOf(shelter.description) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Shelter Name") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = address,
            onValueChange = { address = it },
            label = { Text("Address") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("Phone") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3
        )

        var imageInput by remember {
            mutableStateOf(
                if (shelter.logo.isNotBlank())
                    ImageInputValue.Url(shelter.logo)
                else
                    ImageInputValue.Empty
            )
        }
        val context = LocalContext.current

        ImageInput(
            value = imageInput,
            onValueChange = { imageInput = it },
            modifier = Modifier.fillMaxWidth(),
            label = "Shelter Logo URL"
        )

        Button(
            onClick = {
                onSave(
                    shelter.copy(
                        name = name,
                        address = address,
                        phone = phone,
                        email = email,
                        description = description
                    ),
                    context,
                    imageInput
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Changes")
        }
    }
}
