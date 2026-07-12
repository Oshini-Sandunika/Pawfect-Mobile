package com.example.pawfect_mobile.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
object LoginRoute

@Serializable
object RegisterRoute

@Serializable
object HomeRoute

@Serializable
object SplashRoute

@Serializable
object ProfileRoute

@Serializable
object SearchRoute

@Serializable
object AdoptionCostRoute

@Serializable
object MonthlyCareBudgetRoute

@Serializable
object HumanYearConverterRoute

@Serializable
object FindPerfectPetRoute

@Serializable
data class PetProfileRoute(val petId: String)
