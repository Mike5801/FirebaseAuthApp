package com.example.firebaseauthapp.framework.views

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.firebaseauthapp.databinding.ActivityVerifyOtpBinding
import com.example.firebaseauthapp.framework.viewModel.VerifyOTPViewModel
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

class VerifyOTPActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVerifyOtpBinding
    private val verifyOTPViewModel: VerifyOTPViewModel by viewModels()

    private lateinit var auth: FirebaseAuth
    private lateinit var phone: String
    private lateinit var otp: String
    private lateinit var resendToken : PhoneAuthProvider.ForceResendingToken


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityVerifyOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)


        phone = intent.getStringExtra("phoneNumber").toString()
        otp = intent.getStringExtra("otp").toString()
        resendToken = intent.getParcelableExtra("resendToken")!!

        init()
        resendOTPAvailability()

        binding.btnVerifyOTP.setOnClickListener {
            val typedOtp: String = binding.otp.text.toString()
            if (typedOtp.isNotEmpty() && typedOtp.length == 6) {
                val typedOtp: String = binding.otp.text.toString()
                if (typedOtp.isNotEmpty() && typedOtp.length == 6) {
                    val credential: PhoneAuthCredential = PhoneAuthProvider.getCredential(
                        otp, typedOtp
                    )
                    signInWithPhoneAuthCredential(credential)
                } else {
                    Toast.makeText(this, "Token de verificación por SMS incorrecto", Toast.LENGTH_SHORT)
                }
            }
        }
        binding.newTokenLabel.setOnClickListener {
            resendVerificationCode()
            resendOTPAvailability()
        }

        binding.changePhoneLabel.setOnClickListener {
            val intent = Intent(this, VerifyPhoneActivity::class.java)
            startActivity(intent)
        }
    }

    private fun init () {
        auth = FirebaseAuth.getInstance()
    }

    private fun resendOTPAvailability() {
        binding.newTokenLabel.isEnabled = false
        binding.newTokenLabel.setTextColor(Color.parseColor("#C8C8C8"))

        Handler(Looper.myLooper()!!).postDelayed(Runnable {
            binding.newTokenLabel.isEnabled = true
            binding.newTokenLabel.setTextColor(Color.parseColor("#4CE0D2"))
        }, 60000)
    }

    private fun resendVerificationCode() {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phone)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)                 // Activity (for callback binding)
            .setCallbacks(callbacks)  // OnVerificationStateChangedCallbacks
            .setForceResendingToken(resendToken)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
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
            otp = verificationId
            resendToken = token
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val toast = Toast.makeText(this, "Autenticación Exitosa", Toast.LENGTH_SHORT)
                    var userExists: Boolean
                    verifyOTPViewModel.onValidate(auth.currentUser?.uid.toString())
                    verifyOTPViewModel.exists.observe(this, Observer {
                        userExists = it as Boolean
                        Log.d("user existence", userExists.toString())
                        if (userExists) {
                            toast.show()
                            val intent = Intent(this, MainPageActivity::class.java)
                            intent.putExtra("userUid", auth.currentUser?.uid.toString())
                            startActivity(intent)
                        } else {
                            toast.show()
                            val intent = Intent(this, RegisterUserActivity::class.java)
                            intent.putExtra("uid", auth.currentUser?.uid)
                            startActivity(intent)
                        }
                    })
                    /*
                    val intent = Intent(this, RegisterUserActivity::class.java)
                    intent.putExtra("uid", auth.currentUser?.uid)
                    startActivity(intent)
                     */
                } else {
                    Log.d("Auth", "signInWithPhoneAuthCredential: ${task.exception.toString()}" )
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                    }
                }
            }
    }
}