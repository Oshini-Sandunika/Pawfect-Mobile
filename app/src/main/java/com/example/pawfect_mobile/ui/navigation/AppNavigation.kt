package com.example.pawfect_mobile.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pawfect_mobile.data.AuthService
import com.example.pawfect_mobile.ui.screens.SplashScreen
import com.example.pawfect_mobile.ui.screens.home.HomeScreen
import com.example.pawfect_mobile.ui.screens.login.LoginScreen
import com.example.pawfect_mobile.ui.screens.register.RegisterScreen
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    LaunchedEffect("logout") {
        Firebase.auth.addAuthStateListener {
            if (it.currentUser == null) {
                navController.navigate(LoginRoute) {
                    popUpTo(0) { inclusive = true }
                }
            }
        }
    }

    NavHost(navController = navController, startDestination = SplashRoute) {
        composable<SplashRoute> {
            val isInitialized by AuthService.isInitialized.collectAsState()
            val currentUser by AuthService.currentUserFlow.collectAsState()

            LaunchedEffect(isInitialized, currentUser) {
                if (isInitialized) {
                    if (currentUser != null || Firebase.auth.currentUser != null) {
                        navController.navigate(HomeRoute) {
                            popUpTo<SplashRoute> { inclusive = true }
                        }
                    } else {
                        navController.navigate(LoginRoute) {
                            popUpTo<SplashRoute> { inclusive = true }
                        }
                    }
                }
            }

            SplashScreen()
        }
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
            HomeScreen(onProfileClick = {}, onSearchClick = {}, onPetClick = {})
        }
    }
}
