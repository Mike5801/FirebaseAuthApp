package com.example.firebaseauthapp.framework.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.firebaseauthapp.databinding.ActivityTestingGalleryBinding

class testingGallery : AppCompatActivity() {
    private lateinit var binding: ActivityTestingGalleryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestingGalleryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnEvento.setOnClickListener {
            val intent = Intent(this, ComponentTesting::class.java)
            intent.putExtra("idEvento", "idDelEvento1")
            startActivity(intent)
        }
    }
}