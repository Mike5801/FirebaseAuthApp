package com.example.firebaseauthapp.domain.models

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class FirestoreService {
    private val db = Firebase.firestore
    fun addUserToCollection(user: UserModel, uid: String) {
        db.collection("users")
            .document(uid)
            .set(user)
            .addOnSuccessListener {
                Log.d("FirestoreOutput", "DocumentSnapshot added with ID: $uid")
            }
            .addOnFailureListener { e ->
                Log.w("FirestoreOutput", "Error adding document", e)
            }
        Log.d("Service", "Service")
    }

    suspend fun getUserInformation(uid: String) : DocumentSnapshot {
        var userInformation = db.collection("users")
            .document(uid)
            .get()
            .await()
        return userInformation
    }

    suspend fun userExist(uid: String) : Boolean {
        var exists: Boolean = false
        db.collection("users")
            .document(uid)
            .get()
            .addOnSuccessListener {  document ->
                if (document.exists()) {
                    exists = true
                }
            }.await()
        return exists
    }
}
