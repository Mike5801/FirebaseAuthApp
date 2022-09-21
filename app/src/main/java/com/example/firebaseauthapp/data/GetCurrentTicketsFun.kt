package com.example.firebaseauthapp.data

import com.example.firebaseauthapp.domain.models.AppRepository

class GetCurrentTicketsFun {
    val repository = AppRepository()

    suspend operator fun invoke(idEvent: String, idFuncion: String) : List<Triple<String, Int, Int>> {
        return repository.getCurrentTicketsFun(idEvent, idFuncion)
    }
}