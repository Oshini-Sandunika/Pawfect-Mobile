package com.example.pawfect_mobile.data.models

import java.io.Serializable

class Pet(
    var id: String = "",
    var name: String = "",
    var species: String = "",
    var breed: String = "",
    var age: String = "",
    var description: String = "",
    var imageUrl: String? = null,
    var featured: Boolean = false,
    var adoptionFee: Double = 0.0,
    var monthlyCost: Double = 0.0,
) : Serializable {
}