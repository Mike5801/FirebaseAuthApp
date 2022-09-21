package com.example.firebaseauthapp.data

import com.example.firebaseauthapp.domain.models.AppRepository

class GetDropdownNamesRequirement {
    private val repository = AppRepository()

    suspend operator fun invoke(idEvent: String) : List<Triple<String, Int, String>> {
        return repository.getDropdownNames(idEvent)
    }
}