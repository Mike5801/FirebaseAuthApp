package com.example.firebaseauthapp.framework.viewModel

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebaseauthapp.data.AuthenticateUserRequirement
import com.example.firebaseauthapp.data.GetAuthenticationRequirement
import com.example.firebaseauthapp.data.SendMessageRequirement
import com.example.firebaseauthapp.data.ValidateUserExistenceRequirement
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.launch

class VerifyOTPViewModel: ViewModel() {
    private var validateUserExistenceRequirement = ValidateUserExistenceRequirement()

    val exists = MutableLiveData<Boolean>()
    private lateinit var test: String

    fun onValidate(uid: String){
        viewModelScope.launch {
            val validation = validateUserExistenceRequirement(uid)
            exists.postValue(validation)
            test = "validation"
        }
    }

/*
    private var sendMessageRequirement = SendMessageRequirement()

    private var authenticateUserRequirement = AuthenticateUserRequirement()

    //private var getAuthenticationRequirement = GetAuthenticationRequirement()

    fun sendMessage (phone: String, activity: Activity)
    //: Pair<String, PhoneAuthProvider.ForceResendingToken>
    {
        sendMessageRequirement(phone, activity)
    }

    //fun authenticateUser (typedOtp: String) : Boolean = authenticateUserRequirement(typedOtp)


    //fun getAuthentication() : Boolean = getAuthenticationRequirement()
 */
}