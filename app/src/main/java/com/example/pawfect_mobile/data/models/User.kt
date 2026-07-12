package com.example.pawfect_mobile.data.models

data class User(
    var userId: String = "",
    var fullName: String = "",
    var phone: String = "",
    var shelterId: String? = null,
    var createdAt: Long = 0L
)
