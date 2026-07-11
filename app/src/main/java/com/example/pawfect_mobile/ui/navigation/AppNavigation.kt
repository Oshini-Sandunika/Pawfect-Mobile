package com.example.pawfect_mobile.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pawfect_mobile.Home
import com.example.pawfect_mobile.ui.screens.login.LoginScreen
import com.example.pawfect_mobile.ui.screens.register.RegisterScreen
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    // Check if user is already logged in
    val startDest = if (Firebase.auth.currentUser != null) {
        HomeRoute
    } else {
        LoginRoute
    }

    LaunchedEffect("logout") {
        Firebase.auth.addAuthStateListener {
            if (it.currentUser == null) {
                navController.navigate(LoginRoute)
            }

        }
    }

    NavHost(navController = navController, startDestination = startDest) {
        composable<LoginRoute> {
            LoginScreen(
                onNavigateToRegister = {
                    navController.navigate(RegisterRoute)
                },
                onLoginSuccess = {
                    navController.navigate(HomeRoute) {
                        popUpTo<LoginRoute> { inclusive = true }
                    }
                }
            )
        }

        composable<RegisterRoute> {
            RegisterScreen(
                onNavigateToLogin = {
                    navController.navigate(LoginRoute) {
                        popUpTo<LoginRoute> { inclusive = true }
                    }
                },
                onRegisterSuccess = {
                    navController.navigate(HomeRoute) {
                        popUpTo<LoginRoute> { inclusive = true }
                    }
                }
            )
        }

        composable<HomeRoute> {
            Home()
        }
    }
}
