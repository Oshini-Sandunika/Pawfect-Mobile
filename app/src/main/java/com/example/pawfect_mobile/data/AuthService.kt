package com.example.pawfect_mobile.data

import com.example.pawfect_mobile.data.dto.RegisterRequest
import com.example.pawfect_mobile.data.models.User
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

object AuthService {


    suspend fun createAnonymousAccount() {
        Firebase.auth.signInAnonymously().await()
    }

    suspend fun authenticate(email: String, password: String) {
        Firebase.auth.signInWithEmailAndPassword(email, password).await()
        val userId = Firebase.auth.currentUser?.uid ?: return
        Firebase.firestore.collection("users").document(userId)
            .get().await()
    }

    suspend fun register(dto: RegisterRequest) {
        Firebase.auth.createUserWithEmailAndPassword(dto.email, dto.password).await()
        val userId = Firebase.auth.currentUser?.uid
        if (userId != null) {
            Firebase.firestore.collection("users").document(userId)
                .set(User(userId, dto.fullName, dto.phone, System.currentTimeMillis())).await()
        }
    }

    suspend fun recoverAccount(email: String) {
        Firebase.auth.sendPasswordResetEmail(email).await()
    }

}