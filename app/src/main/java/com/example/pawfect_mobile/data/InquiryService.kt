package com.example.pawfect_mobile.data

import com.example.pawfect_mobile.data.models.Inquiry
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

        val inquiry = Inquiry(
            petId = petId,
            shelterId = shelterId,
            userId = uid,
            message = message,
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

        return snapshot.documents.mapNotNull { it.toObject(Inquiry::class.java) }
            .sortedByDescending { it.timestamp }
    }
}
