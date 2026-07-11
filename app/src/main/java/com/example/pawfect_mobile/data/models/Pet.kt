package com.example.pawfect_mobile.data.models

import java.io.Serializable

class Pet(
    var name: String? = null,
    var species: String? = null,
    var breed: String? = null,
    var age: String? = null,
    var description: String? = null,
    var imageUrl: String? = null,
    var featured: Boolean = false,
    var adoptionFee: Double = 0.0,
    var monthlyCost: Double = 0.0,
) : Serializable