package com.eapro.testingadv

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

//class CustomerDataViewModel(
//    private val repository: CustomerDataRepository
//):ViewModel(){
//
//    private val customerData =MutableLiveData<List<CustomerDataResponse>>()
//
//    fun fetchAndUpdateDataFromServer(newData:List<CustomerDataResponse>){
//        viewModelScope.launch {
//            // Insert new data into the Room database
//            repository.insertData(newData)
//            // Fetch updated data from the Room database
//            customerData.postValue(repository.getAllData())
//
//        }
//    }
//
//
//   fun loadExistingData(){
//       viewModelScope.launch {
//           customerData.postValue(repository.getAllData())
//       }
//   }
//
//}

