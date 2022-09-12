package com.example.firebaseauthapp.domain.models

import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import java.text.ParseException
import java.util.concurrent.TimeUnit

class AuthenticationService {
    /*
    private val auth = FirebaseAuth.getInstance()

    private lateinit var phone: String
    private lateinit var otp: String
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken

    fun sendMessage(phoneNumber: String, activity: Activity )
    //: Pair<String, PhoneAuthProvider.ForceResendingToken>
    {
        phone = phoneNumber
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phone)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(activity)                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
        //return Pair(otp, resendToken)
    }
/*
    fun authenticateUser (typedOtp: String, act: Activity) {
        val credential: PhoneAuthCredential = PhoneAuthProvider.getCredential(, typedOtp)
        signInWithPhoneAuthCredential(credential, act)
    }
    */

    private fun setOtp(verificationId: String) {
        otp = verificationId
    }

    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            //signInWithPhoneAuthCredential(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            if (e is FirebaseAuthInvalidCredentialsException) {
                Log.d("Auth", "onVerificationFailed: ${e.toString()}")
            } else if (e is FirebaseTooManyRequestsException) {
                Log.d("Auth", "onVerificationFailed: ${e.toString()}")
            }
        }

        override fun onCodeSent (
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            setOtp(verificationId)
            resendToken = token
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential, activity: Activity){
        auth.signInWithCredential(credential)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                } else {
                    // Sign in failed, display a message and update the UI
                    Log.d("Auth", "signInWithPhoneAuthCredential: ${task.exception.toString()}" )
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                }
            }
    }

     */
}
