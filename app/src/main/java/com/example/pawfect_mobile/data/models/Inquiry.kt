package com.example.pawfect_mobile.data.models

data class Inquiry(
    var id: String = "",
    val petId: String = "",
    val shelterId: String = "",
    val userId: String = "",
    val message: String = "",
    val timestamp: Long = 0L
)
