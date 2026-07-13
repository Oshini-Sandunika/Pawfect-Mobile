package com.example.pawfect_mobile.data

import android.util.Log
import com.example.pawfect_mobile.data.models.Pet
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.future.future
import kotlinx.coroutines.tasks.await

object PetService {

    suspend fun getFeaturedPets(): List<Pet> {
        val snapshot = Firebase.firestore.collection("pets")
            .whereEqualTo("featured", true)
            .get()
            .await()

        val pets = snapshot.documents.mapNotNull { it.toObject(Pet::class.java) }

        return ShelterService.attachShelters(pets)

    }

    suspend fun getPetById(id: String): Pet? {
        val snapshot = Firebase.firestore.collection("pets")
            .document(id)
            .get().await()

        val pet = snapshot.toObject(Pet::class.java)

        if (pet != null) {
            pet.shelter = ShelterService.getShelterById(pet.shelterId)
        }

        return pet
    }

    fun getPetByIdCB(id: String, onResult: (Pet?) -> Unit) {
        val result = CoroutineScope(Dispatchers.IO).future {
            getPetById(id)
        }
        result.thenAccept(onResult)
            .exceptionally {
                Log.e("PetService", "Failed to fetch pet", it)
                onResult(null)
                null
            }

    }

    suspend fun getByShelterId(shelterId: String): List<Pet> {
        val querySnapshot = Firebase.firestore.collection("pets")
            .whereEqualTo("shelterId", shelterId)
            .get()
            .await()

        return querySnapshot.documents.mapNotNull { it.toObject(Pet::class.java) }
    }

    suspend fun searchPets(query: String, type: String?): List<Pet> {
        val querySnapshot = if (!type.isNullOrBlank() && type != "All") {
            Firebase.firestore.collection("pets")
                .whereEqualTo("species", type)
                .get()
                .await()
        } else if (type == "Other") {
            Firebase.firestore.collection("pets")
                .whereNotIn(
                    "species",
                    mutableListOf("Dog", "Cat", "Bird", "Rabbit", "Fish")
                )
                .get()
                .await()
        } else {
            Firebase.firestore.collection("pets")
                .get()
                .await()
        }

        var allFetched = querySnapshot.documents.mapNotNull { it.toObject(Pet::class.java) }


        if (!query.isBlank()) {
            val q = query.lowercase()
            allFetched = allFetched.filter {
                it.name.lowercase().contains(q) ||
                        it.breed.lowercase().contains(q) ||
                        it.description.lowercase().contains(q)
            }
        }

        return ShelterService.attachShelters(allFetched)
    }

    suspend fun addPet(pet: Pet) {
        val ref = if (pet.id.isEmpty()) {
            Firebase.firestore.collection("pets").document()
        } else {
            Firebase.firestore.collection("pets").document(pet.id)
        }

        pet.id = ref.id
        ref.set(pet).await()
    }

    suspend fun updatePet(pet: Pet) {
        Firebase.firestore.collection("pets").document(pet.id).set(pet).await()
    }

    suspend fun deletePet(petId: String) {
        Firebase.firestore.collection("pets").document(petId).delete().await()
    }
}
