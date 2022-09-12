package com.example.firebaseauthapp.framework.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.firebaseauthapp.databinding.ActivityVerifyPhoneBinding
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit

class VerifyPhoneActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVerifyPhoneBinding
    private lateinit var phone: String
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerifyPhoneBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()

        binding.verifyBtn.setOnClickListener {
            phone = binding.phone.text.toString()
            if (phone != null && phone.isNotEmpty() && phone.length == 10) {
                phone = "+52$phone"
                val options = PhoneAuthOptions.newBuilder(auth)
                    .setPhoneNumber(phone)       // Phone number to verify
                    .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                    .setActivity(this)                 // Activity (for callback binding)
                    .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
                    .build()
                PhoneAuthProvider.verifyPhoneNumber(options)
            } else {
                val text: String = "Por favor ingresa un número válido"
                val duration = Toast.LENGTH_SHORT
                Toast.makeText(this, text, duration).show()
            }
        }
    }

    private fun init() {
        auth = FirebaseAuth.getInstance()
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

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            Log.d("Auth", "onCodeSent:$verificationId")
            val intent = Intent(this@VerifyPhoneActivity, VerifyOTPActivity::class.java)
            intent.putExtra("otp", verificationId)
            intent.putExtra("resendToken", token)
            intent.putExtra("phoneNumber", phone)
            startActivity(intent)
        }
    }
/*
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Autenticación Exitosa", Toast.LENGTH_SHORT).show()
                    sendToMain()
                } else {
                    Log.d("Auth", "signInWithPhoneAuthCredential: ${task.exception.toString()}" )
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                    }

                }
            }
    }

 */
    /*
    override fun onStart() {
        super.onStart()
        if (auth.currentUser != null) {
            startActivity(Intent(this, MainPageActivity::class.java))
        }
    }
    */

}