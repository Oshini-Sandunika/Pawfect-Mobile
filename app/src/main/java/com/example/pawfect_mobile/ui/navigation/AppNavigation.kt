package com.example.pawfect_mobile.ui.navigation

import android.content.Intent
import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pawfect_mobile.PetProfileActivity
import com.example.pawfect_mobile.data.AuthService
import com.example.pawfect_mobile.ui.screens.SplashScreen
import com.example.pawfect_mobile.ui.screens.home.HomeScreen
import com.example.pawfect_mobile.ui.screens.login.LoginScreen
import com.example.pawfect_mobile.ui.screens.profile.ProfileScreen
import com.example.pawfect_mobile.ui.screens.register.RegisterScreen
import com.example.pawfect_mobile.ui.screens.search.SearchScreen
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.serialization.Serializable

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val activity = LocalActivity.current

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
            HomeScreen(
                onProfileClick = {
                    navController.navigate(ProfileRoute)
                },
                onSearchClick = {
                    navController.navigate(SearchRoute)
                },
                onPetClick = {
                    val intent = Intent(activity, PetProfileActivity::class.java)
                    intent.putExtra("PET_ID", it)
                    activity?.startActivity(intent)
                }
            )
        }
        composable<ProfileRoute> {
            ProfileScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        composable<SearchRoute> {
            SearchScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onPetClick = {
                    val intent = Intent(activity, PetProfileActivity::class.java)
                    intent.putExtra("PET_ID", it)
                    activity?.startActivity(intent)
                }
            )
        }
    }
}

@Serializable
object ProfileRoute

@Serializable
object SearchRoute
