package com.example.pawfect_mobile.data

import com.example.pawfect_mobile.data.dto.RegisterRequest
import com.example.pawfect_mobile.data.models.User
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.tasks.await

object AuthService {

    private val _currentUserFlow = MutableStateFlow<User?>(null)
    val currentUserFlow: StateFlow<User?> = _currentUserFlow.asStateFlow()

    private val _isInitialized = MutableStateFlow(false)
    val isInitialized: StateFlow<Boolean> = _isInitialized.asStateFlow()

    suspend fun initialize() {
        val userId = Firebase.auth.currentUser?.uid
        if (userId != null) {
            try {
                val snapshot = Firebase.firestore.collection("users").document(userId).get().await()
                _currentUserFlow.value = snapshot.toObject(User::class.java)
            } catch (e: Exception) {
                // not logged in
            }
        }
        _isInitialized.value = true
    }

    suspend fun authenticate(email: String, password: String) {
        Firebase.auth.signInWithEmailAndPassword(email, password).await()
        val userId = Firebase.auth.currentUser?.uid ?: return
        val snapshot = Firebase.firestore.collection("users").document(userId)
            .get().await()
        _currentUserFlow.value = snapshot.toObject(User::class.java)
    }

    suspend fun register(dto: RegisterRequest) {
        Firebase.auth.createUserWithEmailAndPassword(dto.email, dto.password).await()
        val userId = Firebase.auth.currentUser?.uid
        if (userId != null) {
            val user = User(
                userId,
                dto.fullName,
                phone = dto.phone,
                createdAt = System.currentTimeMillis()
            )
            Firebase.firestore.collection("users").document(userId)
                .set(user).await()
            _currentUserFlow.value = user
        }
    }

    suspend fun recoverAccount(email: String) {
        Firebase.auth.sendPasswordResetEmail(email).await()
    }

    suspend fun updateProfile(fullName: String, phone: String) {
        val user = _currentUserFlow.value ?: return
        val updatedUser = user.copy(fullName = fullName, phone = phone)
        Firebase.firestore.collection("users").document(user.userId)
            .set(updatedUser).await()
        _currentUserFlow.value = updatedUser
    }

    suspend fun updatePassword(newPassword: String) {
        Firebase.auth.currentUser?.updatePassword(newPassword)?.await()
    }

    suspend fun deleteAccount() {
        val user = _currentUserFlow.value ?: return
        Firebase.firestore.collection("users").document(user.userId).delete().await()
        Firebase.auth.currentUser?.delete()?.await()
        _currentUserFlow.value = null
    }

    fun logout() {
        Firebase.auth.signOut()
        _currentUserFlow.value = null
    }

}