package com.example.firebaseauthapp.framework.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.RadioButton
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.firebaseauthapp.databinding.ActivityComponentTestingBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.local.QueryResult
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class ComponentTesting : AppCompatActivity() {
    private lateinit var binding: ActivityComponentTestingBinding
    private lateinit var gender: String
    private val db = Firebase.firestore
    private lateinit var specificUser: DocumentSnapshot
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityComponentTestingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val defaultRadioBtn = binding.male.id

        val maleRadioBtn = binding.male.id
        val femaleRadioBtn = binding.female.id
        val otherRadioBtn = binding.other.id

        binding.editGenderReg2.check(defaultRadioBtn)

        gender = binding.male.text.toString()

        binding.editGenderReg2.setOnCheckedChangeListener { radioGroup, i ->
            when(i) {
                maleRadioBtn -> gender = binding.male.text.toString()
                femaleRadioBtn -> gender = binding.female.text.toString()
                otherRadioBtn -> gender = binding.other.text.toString()
            }
        }

        binding.btn.setOnClickListener {
            Log.d("RadioButton", gender)
        }

        val userData = MutableLiveData<DocumentSnapshot>()

        lifecycleScope.launch {
            val data =  getData("Justino")
            userData.postValue(data)
        }

        userData.observe(this, Observer {
            specificUser = it
            Log.d("Got result", specificUser.data.toString())
        })

        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

        val eventId: String = intent.getStringExtra("idEvento").toString()



    }

    private suspend fun getData(name: String) : DocumentSnapshot {
        val userData = db.collection("users")
            .whereEqualTo("name", "Justino")
            .get()
            .addOnSuccessListener {
                Log.d("Firestore Testing", "Success")
            }
            .await()

        val userAn = db.collection("animes")
            .document(userData.documents[0].id)
            .get()
            .await()
        return userAn
    }
}