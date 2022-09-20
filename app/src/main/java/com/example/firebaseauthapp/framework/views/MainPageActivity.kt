package com.example.firebaseauthapp.framework.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import com.example.firebaseauthapp.databinding.ActivityMainPageBinding
import com.example.firebaseauthapp.framework.viewModel.MainPageViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class MainPageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainPageBinding
    private val mainPageViewModel : MainPageViewModel by viewModels()
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        val userUid: String = intent.getStringExtra("userUid").toString()
        lateinit var name: String
        lateinit var lastName: String
        lateinit var email: String

        mainPageViewModel.getUser(userUid) {
            binding.username.text = it.name
        }

        mainPageViewModel.user.observe(this, Observer {
            binding.username.text = it.name
        })

        binding.logoutBtn.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, VerifyPhoneActivity::class.java))
        }
    }

}