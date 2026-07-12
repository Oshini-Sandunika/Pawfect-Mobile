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

        var pets = snapshot.documents.mapNotNull { it.toObject(Pet::class.java) }
        if (pets.isEmpty()) {
            pets = getPlaceholderPets()
        }

        return ShelterService.attachShelters(pets)

    }

    suspend fun getPetById(id: String): Pet? {
        val snapshot = Firebase.firestore.collection("pets")
            .document(id)
            .get().await()

        val pet =
            snapshot.toObject(Pet::class.java) ?: getPlaceholderPets().find { it.id == id }

        if (pet != null) {
            pet.shelter = ShelterService.getShelterById(id)
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

    suspend fun searchPets(query: String, type: String?): List<Pet> {
        val querySnapshot = if (!type.isNullOrBlank() && type != "All") {
            Firebase.firestore.collection("pets")
                .whereEqualTo("species", type)
                .get()
                .await()
        } else {
            Firebase.firestore.collection("pets")
                .get()
                .await()
        }

        var allFetched = querySnapshot.documents.mapNotNull { it.toObject(Pet::class.java) }

        if (allFetched.isEmpty()) {
            allFetched = mutableListOf()
            repeat(10) { allFetched.addAll(getPlaceholderPets()) }
            allFetched.shuffle()

            if (!type.isNullOrBlank() && type != "All") {
                allFetched = allFetched.filter { it.species.equals(type, ignoreCase = true) }
            }
        }

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


    private fun getPlaceholderPets(): List<Pet> {
        return listOf(
            Pet(
                "1", "Bella", "Dog", "Golden Retriever", "2 years",
                "Friendly and energetic dog.",
                "https://images.unsplash.com/photo-1552053831-71594a27632d?auto=format&fit=crop&q=80&w=500",
                true, 250.0, 50.0, "dummy_shelter"
            ),
            Pet(
                "2", "Luna", "Cat", "Persian", "1 year",
                "Quiet and cuddly companion.",
                "https://images.unsplash.com/photo-1514888286974-6c03e2ca1dba?auto=format&fit=crop&q=80&w=500",
                true, 100.0, 30.0, "dummy_shelter"
            ),
            Pet(
                "3", "Charlie", "Dog", "Beagle", "3 years",
                "Loves the outdoors and howling.",
                "https://images.unsplash.com/photo-1537151608804-ea2f1414360c?auto=format&fit=crop&q=80&w=500",
                true, 200.0, 45.0, "dummy_shelter"
            )
        )
    }
}
