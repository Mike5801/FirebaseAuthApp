package com.example.firebaseauthapp.domain.models

import android.app.Activity
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot

class AppRepository {
    private val fireStoreAPI = FirestoreService()
    private val authAPI = AuthenticationService()

    fun addUser(user: UserModel, uid: String) {
        fireStoreAPI.addUserToCollection(user, uid)
        Log.d("Repository", "Repository")
    }
    suspend fun getUser(uid: String) : DocumentSnapshot {
        return fireStoreAPI.getUserInformation(uid)
    }

    suspend fun validateUserExistence(uid: String) : Boolean {
        return fireStoreAPI.userExist(uid)
    }

    suspend fun getDropdownNames(idEvent: String) : List<Triple<String, Int, String>> {
        return fireStoreAPI.getDropdownNames(idEvent)
    }

    suspend fun getCurrentTicketsFun(idEvent: String, idFuncion: String) : List<Triple<String, Int, Int>> {
        return fireStoreAPI.currentTicketsFun(idEvent, idFuncion)
    }
}