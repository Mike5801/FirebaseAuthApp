package com.example.firebaseauthapp.framework.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebaseauthapp.data.GetCurrentTicketsFun
import com.example.firebaseauthapp.data.GetDropdownNamesRequirement
import kotlinx.coroutines.launch

class ComponentTestingViewModel : ViewModel() {
    private var getDropdownNamesRequirement = GetDropdownNamesRequirement()
    private var getCurrentTicketsFun = GetCurrentTicketsFun()

    val dropDownValues = MutableLiveData<List<Triple<String, Int, String>>>()
    val boletosDisponibles = MutableLiveData<List<Triple<String, Int, Int>>>()

    fun getDropdownNames(idEvento: String) {
        viewModelScope.launch {
            var dropDown = getDropdownNamesRequirement(idEvento)
            dropDownValues.postValue(dropDown)
        }
    }

    fun getCurrentTickets(idEvent: String, idFuncion: String) {
        viewModelScope.launch {
            val currentTickets = getCurrentTicketsFun(idEvent, idFuncion)
            boletosDisponibles.postValue(currentTickets)
        }
    }
}