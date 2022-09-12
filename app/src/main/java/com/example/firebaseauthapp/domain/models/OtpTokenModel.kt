package com.example.firebaseauthapp.domain.models

import com.google.firebase.auth.PhoneAuthProvider

data class OtpTokenModel(
    val otp: String,
    val resendToken: PhoneAuthProvider.ForceResendingToken
)
