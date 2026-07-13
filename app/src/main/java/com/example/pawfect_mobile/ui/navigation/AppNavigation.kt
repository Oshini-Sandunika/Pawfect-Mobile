package com.example.pawfect_mobile.ui.navigation

import android.os.Bundle
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.fragment.compose.AndroidFragment
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.pawfect_mobile.data.AuthService
import com.example.pawfect_mobile.fragments.AdoptionCostFragment
import com.example.pawfect_mobile.fragments.FindPerfectPetFragment
import com.example.pawfect_mobile.fragments.HumanYearConverterFragment
import com.example.pawfect_mobile.fragments.MonthlyCareBudgetFragment
import com.example.pawfect_mobile.fragments.PetProfileFragment
import com.example.pawfect_mobile.ui.components.StyledTopBar
import com.example.pawfect_mobile.ui.layouts.AppLayout
import com.example.pawfect_mobile.ui.screens.SplashScreen
import com.example.pawfect_mobile.ui.screens.home.HomeScreen
import com.example.pawfect_mobile.ui.screens.login.LoginScreen
import com.example.pawfect_mobile.ui.screens.profile.ProfileScreen
import com.example.pawfect_mobile.ui.screens.register.RegisterScreen
import com.example.pawfect_mobile.ui.screens.search.SearchScreen
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

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
                    navController.navigate(PetProfileRoute(it))
                },
                onQuizClick = {
                    navController.navigate(FindPerfectPetRoute)
                },
                onAgeCalculatorClick = {
                    navController.navigate(HumanYearConverterRoute)
                },
                onBudgetClick = {
                    navController.navigate(MonthlyCareBudgetRoute)
                },
                onCostEstimatorClick = {
                    navController.navigate(AdoptionCostRoute)
                },
                onStaffDashboardClick = {
                    navController.navigate(StaffDashboardRoute)
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
                    navController.navigate(PetProfileRoute(it))
                }
            )
        }
        composable<AdoptionCostRoute> {
            AppLayout(topBar = {
                StyledTopBar(
                    title = "Adoption Cost",
                    goBack = { navController.popBackStack() })
            }) {
                AndroidFragment<AdoptionCostFragment>()
            }
        }
        composable<MonthlyCareBudgetRoute> {
            AppLayout(topBar = {
                StyledTopBar(
                    title = "Monthly Care Budget",
                    goBack = { navController.popBackStack() })
            }) {
                AndroidFragment<MonthlyCareBudgetFragment>()
            }
        }
        composable<HumanYearConverterRoute> {
            AppLayout(topBar = {
                StyledTopBar(
                    title = "Human Year Converter",
                    goBack = { navController.popBackStack() })
            }) {
                AndroidFragment<HumanYearConverterFragment>()
            }
        }
        composable<FindPerfectPetRoute> {
            AppLayout(
                noScroll = true,
                noInset = true,
                topBar = {
                    StyledTopBar(
                        title = "Find Perfect Pet",
                        goBack = { navController.popBackStack() })
                }) {
                AndroidFragment<FindPerfectPetFragment>(modifier = Modifier.fillMaxSize())
            }
        }
        composable<PetProfileRoute> { backStackEntry ->
            val route = backStackEntry.toRoute<PetProfileRoute>()
            AppLayout(
                topBar = {
                    StyledTopBar(
                        title = "Pet Profile",
                        goBack = { navController.popBackStack() })
                }) {
                AndroidFragment<PetProfileFragment>(
                    modifier = Modifier.fillMaxSize(),
                    arguments = Bundle().apply { putString("PET_ID", route.petId) }
                )
            }
        }
        composable<StaffDashboardRoute> {
            com.example.pawfect_mobile.ui.screens.staff.StaffDashboardScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToPetEdit = { petId -> navController.navigate(PetEditRoute(petId)) }
            )
        }
        composable<PetEditRoute> { backStackEntry ->
            val route = backStackEntry.toRoute<PetEditRoute>()
            com.example.pawfect_mobile.ui.screens.staff.PetEditScreen(
                petId = route.petId,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
