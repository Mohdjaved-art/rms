package com.eapro.testingadv


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class CustomerViewModel(
    private val repository: CustomerRepository  // Unified repository
) : ViewModel() {

    private val _customerData = MutableLiveData<List<CustomerDataResponse>>()
    val customerData: LiveData<List<CustomerDataResponse>> = _customerData

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    // Function to fetch data from Room and update it from the server
    fun loadAndUpdateCustomerData(serialNumber: String) {
        viewModelScope.launch {
            try {
                // Load existing data from Room
                val roomData = repository.getAllDataFromRoom()
                _customerData.postValue(roomData) // Show cached data immediately

                // Fetch data from the server and update Room
                repository.fetchDataFromServer(serialNumber) { result ->
                    result.onSuccess { serverData ->
                        _customerData.postValue(serverData)  // Update with server data
                        Timber.d("Fetched data from server and updated Room: $serverData")
                    }.onFailure { throwable ->
                        _error.postValue(throwable.message)
                        Timber.e("Error fetching data from server: ${throwable.message}")
                    }
                }
            } catch (e: Exception) {
                _error.postValue(e.message)
                Timber.e("Error loading data: ${e.message}")
            }
        }
    }

    fun getCustomerData(deviceslno: String) {
        viewModelScope.launch {
            try {
                val data = repository.getCustomerData(deviceslno) // Call the combined function
                _customerData.postValue(data)
                Timber.e("room data: ${data}")

            } catch (e: Exception) {
                _error.postValue(e.message)
                Timber.e("Error fetching customer data: ${e.message}")
            }
        }
    }
}





