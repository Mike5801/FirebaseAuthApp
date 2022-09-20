package com.example.firebaseauthapp.framework.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebaseauthapp.data.GetUserInformationRequirement
import com.example.firebaseauthapp.domain.models.UserModel
import kotlinx.coroutines.launch

class MainPageViewModel : ViewModel() {
    private var getUserInformationRequirement = GetUserInformationRequirement()

    val user = MutableLiveData<UserModel>()

    fun getUser(uid: String, result: (UserModel) -> Unit) {
        viewModelScope.launch {
            val userInfo = getUserInformationRequirement(uid).data
            val name = userInfo?.get("name")
            val lastName = userInfo?.get("lastName")
            val email = userInfo?.get("email")
            val userData = UserModel(name.toString(), lastName.toString(), email.toString())
            result(userData)
        }
    }
}