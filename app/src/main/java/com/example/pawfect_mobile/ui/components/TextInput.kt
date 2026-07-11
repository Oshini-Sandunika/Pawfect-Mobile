package com.example.pawfect_mobile.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pawfect_mobile.ui.theme.AppTheme

@Composable
fun TextInput(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    required: Boolean = false,
    description: String = "",
    errorMessage: String? = null,
    validator: ((String) -> String?)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    var internalError by remember { mutableStateOf<String?>(null) }
    var hasFocus by remember { mutableStateOf(false) }

    // Use external error if provided, otherwise internal
    val displayError = errorMessage ?: internalError

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.padding(bottom = 4.dp)) {
            Text(label)
            if (required) {
                Text(
                    "*",
                    fontSize = 14.sp,
                    modifier = Modifier.padding(horizontal = 4.dp),
                    color = Color.Red
                )
            }
        }
        TextField(
            value = value,
            onValueChange = {
                internalError = null
                onValueChange(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    if (hasFocus && !focusState.isFocused) {
                        if (validator != null) {
                            internalError = validator(value)
                        }
                    }
                    hasFocus = focusState.isFocused
                },
            isError = displayError != null,
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            supportingText = {
                if (displayError != null) {
                    Text(displayError, color = MaterialTheme.colorScheme.error)
                } else if (description.isNotEmpty()) {
                    Text(description)
                }
            }
        )
    }
}

@Preview
@Composable
fun TextInputPreview() {
    AppTheme {
        Surface {
            Box(modifier = Modifier.padding(12.dp)) {
                TextInput(
                    label = "Test Input",
                    value = "",
                    onValueChange = {},
                    required = true,
                    description = "Description Here"
                )
            }
        }
    }
}