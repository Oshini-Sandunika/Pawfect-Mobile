package com.example.pawfect_mobile.data

import com.example.pawfect_mobile.data.models.Pet
import com.example.pawfect_mobile.data.models.Shelter
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

object PetService {

    suspend fun getFeaturedPets(): List<Pet> {
        return try {
            val snapshot = Firebase.firestore.collection("pets")
                .whereEqualTo("featured", true)
                .get()
                .await()

            val pets = snapshot.documents.mapNotNull { it.toObject(Pet::class.java) }
            if (pets.isNotEmpty()) {
                attachShelters(pets)
            } else {
                attachShelters(getPlaceholderPets())
            }
        } catch (e: Exception) {
            attachShelters(getPlaceholderPets())
        }
    }

    fun getPetById(id: String, onResult: (Pet?) -> Unit) {
        Firebase.firestore.collection("pets")
            .document(id)
            .get()
            .addOnSuccessListener { snapshot ->
                if (snapshot.exists()) {
                    onResult(snapshot.toObject(Pet::class.java))
                } else {
                    onResult(getPlaceholderPets().find { it.id == id })
                }
            }
            .addOnFailureListener {
                onResult(getPlaceholderPets().find { it.id == id })
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

        return if (query.isBlank()) {
            attachShelters(allFetched)
        } else {
            val q = query.lowercase()
            attachShelters(allFetched.filter {
                it.name.lowercase().contains(q) ||
                        it.breed.lowercase().contains(q) ||
                        it.description.lowercase().contains(q)
            })
        }
    }

    private suspend fun attachShelters(pets: List<Pet>): List<Pet> {
        val shelterMap = mutableMapOf<String, Shelter>()
        for (pet in pets) {
            val sId = pet.shelterId.ifBlank { "dummy_shelter" }
            if (!shelterMap.containsKey(sId)) {
                val shelter = getShelterById(sId)
                if (shelter != null) shelterMap[sId] = shelter
            }
        }
        pets.forEach { 
            val sId = it.shelterId.ifBlank { "dummy_shelter" }
            it.shelter = shelterMap[sId] ?: getPlaceholderShelter() 
        }
        return pets
    }

    suspend fun getShelterById(id: String): Shelter? {
        if (id.isBlank() || id == "dummy_shelter") return getPlaceholderShelter()
        return try {
            val snapshot = Firebase.firestore.collection("shelters").document(id).get().await()
            if (snapshot.exists()) {
                snapshot.toObject(Shelter::class.java)
            } else {
                getPlaceholderShelter()
            }
        } catch (e: Exception) {
            getPlaceholderShelter()
        }
    }

    private fun getPlaceholderPets(): List<Pet> {
        return listOf(
            Pet(
                "1", "Bella", "Dog", "Golden Retriever", "2 years",
                "Friendly and energetic dog.",
                "https://images.unsplash.com/photo-1552053831-71594a27632d?auto=format&fit=crop&q=80&w=500",
                true, 250.0, 50.0
            ),
            Pet(
                "2", "Luna", "Cat", "Persian", "1 year",
                "Quiet and cuddly companion.",
                "https://images.unsplash.com/photo-1514888286974-6c03e2ca1dba?auto=format&fit=crop&q=80&w=500",
                true, 100.0, 30.0
            ),
            Pet(
                "3", "Charlie", "Dog", "Beagle", "3 years",
                "Loves the outdoors and howling.",
                "https://images.unsplash.com/photo-1537151608804-ea2f1414360c?auto=format&fit=crop&q=80&w=500",
                true, 200.0, 45.0
            )
        )
    }

    private fun getPlaceholderShelter(): Shelter {
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
