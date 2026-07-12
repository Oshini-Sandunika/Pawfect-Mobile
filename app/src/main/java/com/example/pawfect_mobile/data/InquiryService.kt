package com.example.pawfect_mobile.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.UUID

object InquiryService {

    fun submitInquiry(petId: String, shelterId: String, message: String, onResult: (Boolean) -> Unit) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: "anonymous"
        val db = FirebaseFirestore.getInstance()
        val inquiryId = UUID.randomUUID().toString()

        val inquiry = mapOf(
            "id" to inquiryId,
            "petId" to petId,
            "shelterId" to shelterId,
            "userId" to uid,
            "message" to message,
            "timestamp" to System.currentTimeMillis()
        )

        db.collection("inquiries").document(inquiryId).set(inquiry)
            .addOnSuccessListener {
                onResult(true)
            }
            .addOnFailureListener {
                onResult(false)
            }
    }
}
