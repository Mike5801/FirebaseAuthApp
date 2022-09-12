package com.example.firebaseauthapp.data

import com.example.firebaseauthapp.domain.models.AppRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot

class GetUserInformationRequirement {
    private val repository = AppRepository()

    suspend operator fun invoke(uid: String) : DocumentSnapshot {
       return repository.getUser(uid)
    }
}