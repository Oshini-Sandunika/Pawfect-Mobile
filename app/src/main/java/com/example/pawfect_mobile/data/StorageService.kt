package com.example.pawfect_mobile.data

import android.content.Context
import android.net.Uri
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import kotlinx.coroutines.tasks.await

object StorageService {

    suspend fun uploadPetImage(context: Context, uri: Uri, shelterId: String, petId: String): String {
        val path = "shelter/${shelterId}/pets/${petId}/image"
        return uploadImage(context, uri, path)
    }

    suspend fun uploadShelterImage(context: Context, uri: Uri, shelterId: String): String {
        val path = "shelter/${shelterId}/logo"
        return uploadImage(context, uri, path)
    }

    // Upload an image with size and format restrictions
    private suspend fun uploadImage(
        context: Context,
        uri: Uri,
        path: String
    ): String {
        val resolver = context.contentResolver
        
        // Check MIME type
        val mimeType = resolver.getType(uri)
        val validMimeTypes = listOf("image/jpeg", "image/png", "video/webm", "image/webm")
        if (mimeType != null && !validMimeTypes.contains(mimeType)) {
            throw IllegalArgumentException("Invalid file type. Only JPG, PNG, and WebM are allowed.")
        }

        // Check file size (5MB limit)
        val cursor = resolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val sizeIndex = it.getColumnIndex(android.provider.OpenableColumns.SIZE)
                if (sizeIndex != -1) {
                    val size = it.getLong(sizeIndex)
                    if (size > 5 * 1024 * 1024) {
                        throw IllegalArgumentException("File size exceeds 5MB limit.")
                    }
                }
            }
        }

        val storageRef = Firebase.storage.reference.child(path)
        
        // Upload file
        storageRef.putFile(uri).await()
        
        // Return download URL
        return storageRef.downloadUrl.await().toString()
    }
}
