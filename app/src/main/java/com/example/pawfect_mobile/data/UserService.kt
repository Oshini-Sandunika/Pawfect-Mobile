package com.example.pawfect_mobile.data

import com.example.pawfect_mobile.data.models.User
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

object UserService {
    suspend fun getUserById(id: String): User? {
        val snapshot = Firebase.firestore.collection("users")
            .document(id)
            .get().await()

        return snapshot.toObject(User::class.java)
    }
}
