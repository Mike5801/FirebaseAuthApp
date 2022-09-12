package com.example.firebaseauthapp.data

import android.app.Activity
import com.example.firebaseauthapp.domain.models.AppRepository
import com.google.firebase.auth.PhoneAuthProvider

class SendMessageRequirement {
    private val repository = AppRepository()
/*
    operator fun invoke(phone: String, activity: Activity)
    //: Pair<String, PhoneAuthProvider.ForceResendingToken>
    {
        repository.sendMessage(phone, activity)
    }
*/
}