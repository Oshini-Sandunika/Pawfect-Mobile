package com.example.pawfect_mobile.data.models

import java.io.Serializable

data class Shelter(
    var id: String = "",
    var name: String = "",
    var address: String = "",
    var phone: String = "",
    var email: String = "",
    var description: String = "",
    var logo: String = "",
    var location: String = "" // plus code
) : Serializable
