package com.example.firebaseauthapp.data

import android.util.Log
import com.example.firebaseauthapp.domain.models.AppRepository
import com.example.firebaseauthapp.domain.models.UserModel

class RegisterUserRequirement {
    private val repository = AppRepository()

    operator fun invoke(user: UserModel, uid: String) {
        repository.addUser(user, uid)
    }

}