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
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pawfect_mobile.ui.theme.AppTheme

enum class InputType {
    TEXT {

    },
    EMAIL {
        override val keyboardType:  KeyboardOptions =
            KeyboardOptions(keyboardType = KeyboardType.Email)

        override fun validate(input: String): String? {
            return if (input.isNotBlank() && !android.util.Patterns.EMAIL_ADDRESS.matcher(input)
                    .matches()
            ) {
                "Invalid email format"
            } else null
        }
    },
    PHONE {
        override val keyboardType: KeyboardOptions =
            KeyboardOptions(keyboardType = KeyboardType.Phone)
    },

    PASSWORD {
        override val visualTransformation: VisualTransformation
            get() = PasswordVisualTransformation()

        override val keyboardType: KeyboardOptions =
            KeyboardOptions(keyboardType = KeyboardType.Password)

        override fun validate(input: String): String? {
            return if (input.isNotBlank() && input.length < 6) {
                "Password must be at least 6 characters"
            } else null
        }

    };

    open val keyboardType: KeyboardOptions get() = KeyboardOptions.Default
    open val visualTransformation: VisualTransformation = VisualTransformation.None;

    open fun validate(input: String): String? {
        return null
    }
}

@Composable
fun TextInput(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    inputType: InputType = InputType.TEXT,
    required: Boolean = false,
    description: String = "",
    errorMessage: String? = null,
    validator: ((String) -> String?)? = null
) {
    var internalError by remember { mutableStateOf<String?>(null) }
    var hasFocus by remember { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }

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
                        // Run default validation first
                        var err = inputType.validate(value)
                        // Run custom validation if default passes or doesn't apply
                        if (err == null && validator != null) {
                            err = validator(value)
                        }
                        internalError = err
                    }
                    hasFocus = focusState.isFocused
                },
            isError = displayError != null,
            visualTransformation = inputType.visualTransformation,
            keyboardOptions = inputType.keyboardType,
            trailingIcon = {
                if (inputType == InputType.PASSWORD) {
                    val icon = if (passwordVisible) {
                        "Hide"
                    } else {
                        "Show"
                    }
                    TextButton(onClick = { passwordVisible = !passwordVisible }) {
                        Text(icon, fontSize = 12.sp)
                    }
                }
            },
            supportingText = {
                if (displayError != null) {
                    Text(displayError, color = MaterialTheme.colorScheme.error)
                } else if (description.isNotEmpty()) {
                    Text(description)
                }
            },
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
                    label = "Test Password",
                    value = "",
                    onValueChange = {},
                    inputType = InputType.PASSWORD,
                    required = true,
                    description = "Description Here"
                )
            }
        }
    }
}