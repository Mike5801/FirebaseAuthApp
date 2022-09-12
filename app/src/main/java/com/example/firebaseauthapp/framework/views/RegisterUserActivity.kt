package com.example.firebaseauthapp.framework.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.example.firebaseauthapp.databinding.ActivityRegisterUserBinding
import com.example.firebaseauthapp.domain.models.UserModel
import com.example.firebaseauthapp.framework.viewModel.RegisterUserViewModel
import com.google.firebase.firestore.auth.User

class RegisterUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterUserBinding
    private val registerUserViewModel: RegisterUserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterUserBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val userUid: String = intent.getStringExtra("uid").toString()

        binding.btnRegister.setOnClickListener {
            if (!binding.name.text.toString().isNullOrEmpty()
                && !binding.lastName.text.toString().isNullOrEmpty()
                && !binding.email.text.toString().isNullOrEmpty()) {
                val newUser = UserModel(binding.name.text.toString(), binding.lastName.text.toString(), binding.email.text.toString())
                registerUserViewModel.onAddUser(newUser, userUid)
                val intent = Intent(this, MainPageActivity::class.java)
                intent.putExtra("userUid", userUid)
                startActivity(intent)
            } else {
                val toast = Toast.makeText(this, "Por favor llena todos los campos", Toast.LENGTH_SHORT)
                toast.show()
            }
        }
    }
}