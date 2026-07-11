package com.example.pawfect_mobile.ui.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pawfect_mobile.data.AuthService
import com.example.pawfect_mobile.data.dto.RegisterRequest
import com.example.pawfect_mobile.ui.components.TextInput
import com.example.pawfect_mobile.ui.layouts.LoginLayout
import kotlinx.coroutines.launch

@Composable
fun Register(
    onNavigateToLogin: () -> Unit = {},
    onRegisterSuccess: () -> Unit = {}
) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    
    var fullNameError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var phoneError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var confirmPasswordError by remember { mutableStateOf<String?>(null) }
    var registerError by remember { mutableStateOf<String?>(null) }

    val coroutineScope = rememberCoroutineScope()

    val validateFullName: (String) -> String? = {
        if (it.isBlank()) "Full Name cannot be empty" else null
    }

    val validateEmail: (String) -> String? = {
        if (it.isBlank()) "Email cannot be empty"
        else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches()) "Invalid email format"
        else null
    }

    val validatePhone: (String) -> String? = {
        if (it.isBlank()) "Phone cannot be empty" else null
    }

    val validatePassword: (String) -> String? = {
        if (it.isBlank()) "Password cannot be empty"
        else if (it.length < 6) "Password must be at least 6 characters"
        else null
    }

    val validateConfirmPassword: (String) -> String? = {
        if (it.isBlank()) "Please confirm your password"
        else if (it != password) "Passwords do not match"
        else null
    }

    fun validateAndRegister() {
        fullNameError = validateFullName(fullName)
        emailError = validateEmail(email)
        phoneError = validatePhone(phone)
        passwordError = validatePassword(password)
        confirmPasswordError = validateConfirmPassword(confirmPassword)

        if (fullNameError == null && emailError == null && phoneError == null && 
            passwordError == null && confirmPasswordError == null) {
            coroutineScope.launch {
                try {
                    val request = RegisterRequest(
                        email = email,
                        password = password,
                        fullName = fullName,
                        phone = phone
                    )
                    AuthService.register(request)
                    onRegisterSuccess()
                } catch (e: Exception) {
                    registerError = e.message ?: "Registration failed"
                }
            }
        }
    }

    LoginLayout(
        title = "Create an Account",
        subtitle = "Join Pawfect to find your furry friend"
    ) {
        TextInput(
            label = "Full Name",
            value = fullName,
            onValueChange = { 
                fullName = it
                fullNameError = null
                registerError = null
            },
            errorMessage = fullNameError,
            validator = validateFullName
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextInput(
            label = "Email",
            value = email,
            onValueChange = { 
                email = it
                emailError = null
                registerError = null
            },
            errorMessage = emailError,
            validator = validateEmail,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextInput(
            label = "Phone",
            value = phone,
            onValueChange = { 
                phone = it
                phoneError = null
                registerError = null
            },
            errorMessage = phoneError,
            validator = validatePhone,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextInput(
            label = "Password",
            value = password,
            onValueChange = { 
                password = it
                passwordError = null
                registerError = null
            },
            errorMessage = passwordError,
            validator = validatePassword,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextInput(
            label = "Confirm Password",
            value = confirmPassword,
            onValueChange = { 
                confirmPassword = it
                confirmPasswordError = null
                registerError = null
            },
            errorMessage = confirmPasswordError,
            validator = validateConfirmPassword,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
        
        if (registerError != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(registerError!!, color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { validateAndRegister() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Register")
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = onNavigateToLogin) {
            Text("Already have an account? Login")
        }
    }
}

@Preview
@Composable
fun RegisterPreview() {
    Register()
}
