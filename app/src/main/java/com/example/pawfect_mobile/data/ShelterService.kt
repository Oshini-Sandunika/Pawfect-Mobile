package com.example.pawfect_mobile.data

import com.example.pawfect_mobile.data.models.Pet
import com.example.pawfect_mobile.data.models.Shelter
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

object ShelterService {
    suspend fun getShelterById(id: String): Shelter? {
        val snapshot = Firebase.firestore.collection("shelters").document(id).get().await()
        return if (snapshot.exists()) {
            snapshot.toObject(Shelter::class.java)
        } else {
            getPlaceholderShelter()
        }
    }

    suspend fun attachShelters(pets: List<Pet>): List<Pet> {
        val shelterMap = mutableMapOf<String, Shelter?>()
        for (pet in pets) {
            val sId = pet.shelterId
            
            if (!shelterMap.containsKey(sId)) {
                shelterMap[sId] = getShelterById(sId)
            }

            pet.shelter = shelterMap[sId]
        }

        return pets
    }

    fun getPlaceholderShelter(): Shelter {
        return Shelter(
            id = "dummy_shelter",
            name = "Happy Tails Rescue",
            address = "123 Pet Lane, Animal City",
            phone = "555-0199",
            email = "contact@happytails.org",
            description = "A loving place for rescued pets.",
            logo = "https://images.unsplash.com/photo-1548802673-380ab8ebc7b7?auto=format&fit=crop&q=80&w=200",
            location = "84VW+32 Animal City"
        )
    }
}
