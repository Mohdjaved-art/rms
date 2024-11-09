package com.eapro.testingadv

import androidx.lifecycle.*
import kotlinx.coroutines.launch
//
//class CustomerDownloadReprt (private val apiService: ApiService) {
//    suspend fun getCustomerData(
//        deviceslno: String,
//        date: String,
//        date2: String
//    ): List<CustomerDataResponse> {
//        return apiService.downloadCustomerData(
//            CustomerDataRequest(deviceslno, date, date2)
//        )
//    }
//}
//
//
//
//class CustomerDataViewModel(private val repository: CustomerDataRepository) : ViewModel() {
//
//    private val _customerData = MutableLiveData<List<CustomerDataResponse>>()
//    val customerData: LiveData<List<CustomerDataResponse>> = _customerData
//
//    fun fetchCustomerData(deviceslno: String, date: String, date2: String) {
//        viewModelScope.launch {
//            try {
//                val data = repository.getCustomerData(deviceslno, date, date2)
//                _customerData.value = data
//            } catch (e: Exception) {
//                // Handle error (e.g., show error message)
//            }
//        }
//    }
//}
//
//class CustomerDataViewModelFactory(private val repository: CustomerDataRepository) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(CustomerDataViewModel::class.java)) {
//            return CustomerDataViewModel(repository) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}
//

