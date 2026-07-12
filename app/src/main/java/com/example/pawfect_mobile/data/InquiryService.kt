package com.example.pawfect_mobile.data

import com.example.pawfect_mobile.data.models.Inquiry
import com.example.pawfect_mobile.data.models.Pet
import com.example.pawfect_mobile.data.models.User
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

object InquiryService {

    fun submitInquiry(
        petId: String,
        shelterId: String,
        message: String,
        onResult: (Boolean) -> Unit
    ) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid!!
        val email = FirebaseAuth.getInstance().currentUser?.email!!

        val inquiry = Inquiry(
            petId = petId,
            shelterId = shelterId,
            userId = uid,
            message = message,
            email = email,
            timestamp = System.currentTimeMillis()
        )

        val ref = Firebase.firestore.collection("inquiries").document()
        inquiry.id = ref.id
        ref.set(inquiry).addOnSuccessListener {
            onResult(true)
        }
            .addOnFailureListener {
                onResult(false)
            }
    }

    suspend fun getInquiriesForShelter(shelterId: String): List<Inquiry> {
        val snapshot = FirebaseFirestore.getInstance().collection("inquiries")
            .whereEqualTo("shelterId", shelterId)
            .get()
            .await()

        val petCache = mutableMapOf<String, Pet?>()
        val userCache = mutableMapOf<String, User?>()

        return snapshot.documents.mapNotNull {
            val inquiry = it.toObject(Inquiry::class.java)
            if (inquiry !== null && !inquiry.petId.isEmpty()) {
                val pet = if (petCache.contains(inquiry.petId)) {
                    petCache[inquiry.petId]
                } else {
                    val pet = PetService.getPetById(inquiry.petId)
                    petCache[inquiry.petId] = pet
                    pet
                }
                inquiry.pet = pet
            }

            if (inquiry !== null) {
                val user = if (petCache.contains(inquiry.userId)) {
                    userCache[inquiry.userId]
                } else {
                    val user = UserService.getUserById(inquiry.userId)
                    userCache[inquiry.userId] = user
                    user
                }
                inquiry.user = user
            }

            return@mapNotNull inquiry
        }
            .sortedByDescending { it.timestamp }
    }
}
