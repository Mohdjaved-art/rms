package com.eapro.testingadv

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

//
class AlertViewModel(private val repository: AlertRepository) : ViewModel() {

    private val _alerts = MutableLiveData<List<AlertResponse>?>()
    val alerts: MutableLiveData<List<AlertResponse>?> get() = _alerts

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun fetchAlerts(deviceslno: String) {
        viewModelScope.launch {
            try {
                val alertsList = repository.getAlerts(deviceslno)
                if (alertsList != null) {
                    _alerts.postValue(alertsList)
                    Log.d("alerts",""+alertsList.toString())
                } else {
                    _error.postValue("Error fetching alerts")
                }
            } catch (e: Exception) {
                _error.postValue("Failed to load alerts: ${e.message}")
            }
        }
    }
}
