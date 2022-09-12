package com.example.firebaseauthapp.domain.models

import android.app.Activity
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.DocumentSnapshot

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


/* AuthService
    fun sendMessage(phone: String, activity: Activity)
    //: Pair<String, PhoneAuthProvider.ForceResendingToken>
    {
        return authAPI.sendMessage(phone, activity)
    }

 */
/*
    fun authenticateUser(typedOtp: String) {
        authAPI.authenticateUser(typedOtp)
    }
    */


    //fun getAuthentication() : Boolean = authAPI.getAuthenticationResult()



}