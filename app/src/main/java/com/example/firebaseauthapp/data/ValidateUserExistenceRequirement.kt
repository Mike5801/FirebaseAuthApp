package com.example.firebaseauthapp.data

import com.example.firebaseauthapp.domain.models.AppRepository

class ValidateUserExistenceRequirement {
    val repository = AppRepository()

    suspend operator fun invoke(uid: String) : Boolean {
        return repository.validateUserExistence(uid)
    }
}