package com.example.pawfect_mobile.data

import com.example.pawfect_mobile.data.models.Pet
import com.example.pawfect_mobile.data.models.Shelter
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

object ShelterService {
    suspend fun getShelterById(id: String): Shelter? {
        val snapshot = Firebase.firestore.collection("shelters").document(id).get().await()
        return snapshot.toObject(Shelter::class.java)
    }

    suspend fun updateShelter(shelter: Shelter) {
        Firebase.firestore.collection("shelters").document(shelter.id).set(shelter).await()
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

}
