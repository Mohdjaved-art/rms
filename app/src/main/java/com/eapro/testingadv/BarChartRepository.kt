package com.eapro.testingadv

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.launch






class BarChartRepository(private val apiService: ApiService) {

    suspend fun fetchBarChartData(deviceSerialNo: String): List<BarChartData> {
        Log.d("BarChartRepository", "Fetching bar chart data for serial number: $deviceSerialNo")

        val payload = mapOf("deviceslno" to deviceSerialNo)
        val result = try {
            val data = apiService.getBarChartData(payload)
            Log.d("BarChartRepository", "Data fetched successfully: $data")
            data
        } catch (e: Exception) {
            Log.e("BarChartRepository", "Error fetching bar chart data", e)
            emptyList<BarChartData>() // Return an empty list in case of error
        }
        return result
    }
}

class BarChartViewModel(private val repository: BarChartRepository) : ViewModel() {

    private val _barChartData = MutableLiveData<List<BarChartData>>()
    val barChartData: LiveData<List<BarChartData>> get() = _barChartData

    fun loadBarChartData(deviceSerialNo: String) {
        Log.d("BarChartViewModel", "Loading bar chart data for serial number: $deviceSerialNo")

        viewModelScope.launch {
            try {
                val data = repository.fetchBarChartData(deviceSerialNo) // here we have a suspended function
                Log.d("BarChartViewModel", "Data loaded successfully: $data")

                _barChartData.value = data
            } catch (e: Exception) {
                Log.e("BarChartViewModel", "Error fetching bar chart data", e)
            }
        }
    }
}

class BarChartViewModelFactory(private val repository: BarChartRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        Log.d("BarChartViewModelFactory", "Creating BarChartViewModel")
        if (modelClass.isAssignableFrom(BarChartViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            Log.d("BarChartViewModelFactory", "BarChartViewModel created successfully")
            return BarChartViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
