package com.eapro.testingadv

import android.annotation.SuppressLint
import android.content.Context.MODE_PRIVATE
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import timber.log.Timber


//class SwitchViewModel(private val repository: SwitchRepository) : ViewModel() {
//
//    private val _apiResponse = MutableLiveData<ApiResponse>()
//    val apiResponse: LiveData<ApiResponse> get() = _apiResponse
//
//    private val _error = MutableLiveData<String>()
//    val error: LiveData<String> get() = _error
//
//    @SuppressLint("TimberArgCount")
//    fun sendDeviceStatus(deviceStatus: DeviceStatus) {
//        viewModelScope.launch {
//            try {
//                Timber.tag("devicestatus")
//                    .d(
//                        "%s%s",
//                        "%s, deviceSlNo=",
//                        "Sending device status: charging=%s",
//                        deviceStatus.onoff,
//                        deviceStatus.deviceslno
//                    )
//
//                // Call the repository function
//                val response = repository.sendDeviceStatus(deviceStatus)
//
//                // Check for null values in the response and log them
//                val userId = response.USERID ?: "Unknown"
//                val responseMessage = response.RESPONSE ?: "No response message"
//                val message = response.MESSAGE ?: "No message"
//
//                Timber.tag("SwitchViewModel")
//                    .d("%s%s", "%s, MESSAGE=", "%s%s", "%s, RESPONSE=", "Response received: USERID=%s", userId, responseMessage, message)
//
//                // Log if specific fields are null
//                if (response.USERID == null) {
//                    Timber.tag("useridok").e("USERID is null in the response")
//                }
//
//                // Post the response to LiveData
//                _apiResponse.postValue(response)
//            } catch (e: Exception) {
//                // Handle the exception and post the error message to LiveData
//                _error.postValue(e.message ?: "An unknown error occurred")
//                Timber.tag("crock").e("Error sending device status: %s", e.message)
//            }
//        }
//    }
//}


class ChangeSwitchViewModel(private val repository: ChangeSwitchRepository) : ViewModel() {

    private val _apiResponse = MutableLiveData<ApiResponse>()
    val apiResponse: LiveData<ApiResponse> get() = _apiResponse  // working now

    private val _toggleState = MutableLiveData<Boolean>()
    val toggleState: LiveData<Boolean> get() = _toggleState


//    init {
//        // Load initial state from SharedPreferences if needed
//        val sharedPrefs = repository.getApplication().getSharedPreferences("toggle_state_prefs", MODE_PRIVATE)
//        _toggleState.value = sharedPrefs.getBoolean("toggle_state", false)
//    }


    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    @SuppressLint("TimberArgCount")
    fun sendDeviceChangeStatus(changedeviceStatus: deviceswitch) {
        viewModelScope.launch {
            try {
                Timber.tag("devicestatus")
                    .d(
                        "%s%s",
                        "%s, deviceSlNo=",
                        "Sending device status: charging=%s",
                        changedeviceStatus.switchmode,
                        changedeviceStatus.deviceslno
                    )

                // Call the repository function
                val response = repository.sendDeviceChangeStatus(changedeviceStatus)

                // Check for null values in the response and log them
                val userId = response.USERID ?: "Unknown"
                val responseMessage = response.RESPONSE ?: "No response message"
                val message = response.MESSAGE ?: "No message"

                Timber.tag("SwitchViewModel")
                    .d("%s%s", "%s, MESSAGE=", "%s%s", "%s, RESPONSE=", "Response received: USERID=%s", userId, responseMessage, message)

                // Log if specific fields are null
                if (response.USERID == null) {
                    Timber.tag("useridok").e("USERID is null in the response")
                }

                // Post the response to LiveData
                _apiResponse.postValue(response)
            } catch (e: Exception) {
                // Handle the exception and post the error message to LiveData
                _error.postValue(e.message ?: "An unknown error occurred")
                Timber.tag("crock").e("Error sending device status: %s", e.message)
            }
        }
    }
}



//
//class LowSwitchViewModel(private val repository: LowSwitchRepository) : ViewModel() {
//
//    private val _apiResponse = MutableLiveData<ApiResponse>()
//    val apiResponse: LiveData<ApiResponse> get() = _apiResponse
//
//    private val _error = MutableLiveData<String>()
//    val error: LiveData<String> get() = _error
//
//    @SuppressLint("TimberArgCount")
//    fun sendDeviceLowStatus(changeLowStatus: Lowswitch) {
//        viewModelScope.launch {
//            try {
//                Timber.tag("devicestatus")
//                    .d(
//                        "%s%s",
//                        "%s, deviceSlNo=",
//                        "Sending device status: charging=%s",
//                        changeLowStatus.charging,
//                        changeLowStatus.deviceslno
//                    )
//
//                // Call the repository function
//                val response = repository.sendDeviceLowStatus(changeLowStatus)
//
//                // Check for null values in the response and log them
//                val userId = response.USERID ?: "Unknown"
//                val responseMessage = response.RESPONSE ?: "No response message"
//                val message = response.MESSAGE ?: "No message"
//
//                Timber.tag("SwitchViewModel")
//                    .d("%s%s", "%s, MESSAGE=", "%s%s", "%s, RESPONSE=", "Response received: USERID=%s", userId, responseMessage, message)
//
//                // Log if specific fields are null
//                if (response.USERID == null) {
//                    Timber.tag("useridok").e("USERID is null in the response")
//                }
//
//                // Post the response to LiveData
//                _apiResponse.postValue(response)
//            } catch (e: Exception) {
//                // Handle the exception and post the error message to LiveData
//                _error.postValue(e.message ?: "An unknown error occurred")
//                Timber.tag("crock").e("Error sending device status: %s", e.message)
//            }
//        }
//    }
//}