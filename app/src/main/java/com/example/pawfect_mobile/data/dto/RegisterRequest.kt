package com.example.pawfect_mobile.data.dto

data class RegisterRequest(
    val email: String,
    val password: String,
    val fullName: String,
    val phone: String,
)