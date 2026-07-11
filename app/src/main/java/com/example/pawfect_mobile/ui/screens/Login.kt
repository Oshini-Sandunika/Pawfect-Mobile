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
import com.example.pawfect_mobile.ui.components.TextInput
import com.example.pawfect_mobile.ui.layouts.LoginLayout
import kotlinx.coroutines.launch

@Composable
fun Login(
    onNavigateToRegister: () -> Unit = {},
    onLoginSuccess: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var loginError by remember { mutableStateOf<String?>(null) }
    
    val coroutineScope = rememberCoroutineScope()

    val validateEmail: (String) -> String? = {
        if (it.isBlank()) "Email cannot be empty"
        else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches()) "Invalid email format"
        else null
    }

    val validatePassword: (String) -> String? = {
        if (it.isBlank()) "Password cannot be empty"
        else null
    }

    fun validateAndLogin() {
        emailError = validateEmail(email)
        passwordError = validatePassword(password)

        if (emailError == null && passwordError == null) {
            coroutineScope.launch {
                try {
                    AuthService.authenticate(email, password)
                    onLoginSuccess()
                } catch (e: Exception) {
                    loginError = e.message ?: "Login failed"
                }
            }
        }
    }

    LoginLayout(
        title = "Welcome back!",
        subtitle = "Enter your login details to continue"
    ) {
        TextInput(
            label = "Email",
            value = email,
            onValueChange = { 
                email = it
                emailError = null
                loginError = null
            },
            errorMessage = emailError,
            validator = validateEmail,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextInput(
            label = "Password",
            value = password,
            onValueChange = { 
                password = it
                passwordError = null
                loginError = null
            },
            errorMessage = passwordError,
            validator = validatePassword,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
        
        if (loginError != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(loginError!!, color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { validateAndLogin() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = onNavigateToRegister) {
            Text("Don't have an account? Register")
        }
    }
}

@Preview
@Composable
fun LoginPreview() {
    Login()
}