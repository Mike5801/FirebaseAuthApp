package com.example.firebaseauthapp.framework.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebaseauthapp.domain.models.UserModel
import kotlinx.coroutines.launch

class RegisterUserViewModel:ViewModel() {
    private var registerUserRequirement = com.example.firebaseauthapp.data.RegisterUserRequirement()

    fun onAddUser(user: UserModel, uid: String) {
            registerUserRequirement(user, uid)
            Log.d("ViewModel", "ViewModel")
    }
}