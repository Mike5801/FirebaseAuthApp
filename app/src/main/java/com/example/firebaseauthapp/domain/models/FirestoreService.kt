package com.example.firebaseauthapp.domain.models

import android.content.ContentValues.TAG
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.getField
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.lang.Integer.parseInt

class FirestoreService {
    private val db = Firebase.firestore
    fun addUserToCollection(user: UserModel, uid: String) {
        db.collection("users")
            .document(uid)
            .set(user)
            .addOnSuccessListener {
                Log.d("FirestoreOutput", "DocumentSnapshot added with ID: $uid")
            }
            .addOnFailureListener { e ->
                Log.w("FirestoreOutput", "Error adding document", e)
            }
        Log.d("Service", "Service")
    }

    suspend fun getUserInformation(uid: String) : DocumentSnapshot {
        var userInformation = db.collection("users")
            .document(uid)
            .get()
            .await()
        return userInformation
    }

    suspend fun userExist(uid: String) : Boolean {
        var exists: Boolean = false
        db.collection("users")
            .document(uid)
            .get()
            .addOnSuccessListener {  document ->
                if (document.exists()) {
                    exists = true
                }
            }.await()
        return exists
    }

    suspend fun currentTicketsFun(idEvent: String, idFuncion: String) : List<Triple<String, Int, Int>> {
        val maxCountEvent: MutableList<Triple<String, Int, Int>> = mutableListOf()
        val tipoEventoBoleto = db.collection("evento-tipoBoleto")
            .whereEqualTo("idEvento", idEvent)
            .get()
            .await()
        for (document in tipoEventoBoleto) {
            val boletosEventoTipo = db.collection("boleto")
                .whereEqualTo("idFuncion",idFuncion)
                .whereEqualTo("idTipoBoleto", document.data.get("idTipoBoleto"))
                .get()
                .await()
            maxCountEvent.add(Triple(document.data.get("idTipoBoleto").toString(), boletosEventoTipo.documents.size, parseInt(document.data.get("maxBoletos").toString())))
        }
        return maxCountEvent
    }

    suspend fun getDropdownNames(idEvent: String) : List<Triple<String, Int, String>> {
        var dropDown : MutableList<Triple<String, Int, String>> = mutableListOf<Triple<String, Int, String>>()
        val ticketInfo = db.collection("evento-tipoBoleto")
            .whereEqualTo("idEvento", idEvent)
            .get()
            .await()
        for (id in ticketInfo) {
            var info = id.getField<String>("idTipoBoleto").toString()
            var precio = id.getField<Int>("precio") as Int
            val name = db.collection("tipoBoleto")
                .document(info)
                .get()
                .await()
            dropDown.add(Triple(name.data?.get("nombre").toString(), precio, name.id))

            //Log.d("LOG Dropdown: ", "${listDropdown.toString()}")
        }
        return dropDown
    }
}
