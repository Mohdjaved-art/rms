package com.eapro.testingadv


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


import timber.log.Timber

//class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {
//
//    private val _emailResponse = MutableLiveData<String>()
//    val emailResponse: LiveData<String> get() = _emailResponse
//
//    private val _error = MutableLiveData<String>()
//    val error: LiveData<String> get() = _error
//
//    fun sendEmail(emailId: String) {
//        viewModelScope.launch {
//            try {
//                val email = mapOf("emailid" to emailId)
//                val response = authRepository.sendMail(email)
//
//                if (response.isSuccessful) {
//                    _emailResponse.postValue(response.body())
//                    Timber.d("Email sent successfully: ${response.body()}")
//                } else {
//                    _error.postValue("Error: ${response.code()}")
//                    Timber.e("Failed to send email: ${response.code()}")
//                }
//            } catch (e: Exception) {
//                _error.postValue("Exception: ${e.message}")
//                Timber.e(e, "API Exception: ${e.message}")
//            }
//        }
//    }
//}

//class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {
//
//    private val _emailResponse = MutableLiveData<String>()
//    val emailResponse: LiveData<String> get() = _emailResponse
//
//    private val _error = MutableLiveData<String>()
//    val error: LiveData<String> get() = _error
//
//
////    fun sendEmail(emailId: String) {
////        viewModelScope.launch {
////            try {
////                val email = mapOf("emailid" to emailId)
////                val response = authRepository.sendMail(email)
////
////                if (response.isSuccessful) {
////                    // The response is a simple string, so we treat it as such
////                    val responseBody = response.body() ?: ""
////                    Log.d("AuthViewModel", "Response: $responseBody")
////
////                    // Handle "valid" or "invalid" email response
////                    if (responseBody.contains("valid", ignoreCase = true)) {
////                        _emailResponse.postValue("Email is valid. Password reset link sent.")
////                    } else if (responseBody.contains("invalid", ignoreCase = true)) {
////                        _emailResponse.postValue("Email is invalid. Please try again.")
////                    } else {
////                        _emailResponse.postValue("Unexpected response: $responseBody")
////                    }
////                } else {
////                    // Handle non-2xx HTTP errors
////                    val errorBody = response.errorBody()?.string() ?: "Unknown error"
////                    Log.e("AuthViewModel", "Failed to send email - Code: ${response.code()}, Error: $errorBody")
////                    _error.postValue("Error: ${response.code()} - $errorBody")
////                }
////            } catch (e: Exception) {
////                // Handle general exceptions
////                Log.e("AuthViewModel", "Exception occurred while sending email: ${e.message}", e)
////                _error.postValue("Exception: ${e.message}")
////            }
////        }
////    }
//
//
//
////    fun sendEmail(emailList: List<String>) {
////        viewModelScope.launch {
////            try {
////                // Prepare a map with the list of emails
////                val emailMap = mapOf("emails" to emailList)
////
////                // Call the API and pass the email map
////                val response = authRepository.sendMail(emailMap)
////
////                if (response.isSuccessful) {
////                    val responseBody = response.body() ?: ""
////                    Timber.d("Emails sent successfully: $responseBody")
////                    _emailResponse.postValue(responseBody)
////                } else {
////                    val errorBody = response.errorBody()?.string() ?: "Unknown error"
////                    Timber.e("Failed to send email - Code: ${response.code()}, Error: $errorBody")
////                    _error.postValue("Error: ${response.code()} - $errorBody")
////                }
////            } catch (e: Exception) {
////                Timber.e(e, "Exception occurred while sending email: ${e.message}")
////                _error.postValue("Exception: ${e.message}")
////            }
////        }
////    }
//
//
//    fun sendEmail(email: String) {
//        viewModelScope.launch {
//            try {
//                // Prepare the email payload
//                val emailMap = mapOf("emailid" to email)
//
//                // Call the API and pass the email map
//                val response = authRepository.sendMail(emailMap)
//
//                if (response.isSuccessful) {
//                    // Since the response is a simple string, treat it as such
//                    val responseBody = response.body() ?: ""
//                    Timber.d("Email sent successfully: $responseBody")
//                    _emailResponse.postValue(responseBody)
//                } else {
//                    // Handle non-2xx HTTP errors
//                    val errorBody = response.errorBody()?.string() ?: "Unknown error"
//                    Timber.e("Failed to send email - Code: ${response.code()}, Error: $errorBody")
//                    _error.postValue("Error: ${response.code()} - $errorBody")
//                }
//            } catch (e: Exception) {
//                // Handle general exceptions
//                Timber.e(e, "Exception occurred while sending email: ${e.message}")
//                _error.postValue("Exception: ${e.message}")
//            }
//        }
//    }
//
//
//
//
//
//}

//class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {
//
//    private val _emailResponse = MutableLiveData<String>()
//    val emailResponse: LiveData<String> get() = _emailResponse
//
//    private val _otpResponse = MutableLiveData<String>()
//    val otpResponse: LiveData<String> get() = _otpResponse
//
//    private val _error = MutableLiveData<String>()
//    val error: LiveData<String> get() = _error
//
//    // Send Email and then OTP Verification
//    fun sendEmailAndVerifyOtp(email: String, userId: String) {
//        viewModelScope.launch {
//            try {
//                // Step 1: Send the email
//                val emailMap = mapOf("emailid" to email)
//                val emailResponse = authRepository.sendMail(emailMap)
//
//                if (emailResponse.isSuccessful) {
//                    Timber.d("Email sent successfully: ${emailResponse.body()}")
//                    _emailResponse.postValue(emailResponse.body())
//
//                    // Step 2: Call OTP verification API
//                    val otpMap = mapOf("USERID" to userId)
//                    val otpResponse = authRepository.verifyOtp(otpMap)
//
//                    if (otpResponse.isSuccessful) {
//                        Timber.d("OTP sent successfully: ${otpResponse.body()}")
//                        _otpResponse.postValue(otpResponse.body())
//                    } else {
//                        val otpErrorBody = otpResponse.errorBody()?.string() ?: "Unknown OTP error"
//                        Timber.e("OTP verification failed: Code: ${otpResponse.code()}, Error: $otpErrorBody")
//                        _error.postValue("Error: ${otpResponse.code()} - $otpErrorBody")
//                    }
//
//                } else {
//                    val emailErrorBody = emailResponse.errorBody()?.string() ?: "Unknown email sending error"
//                    Timber.e("Failed to send email: Code: ${emailResponse.code()}, Error: $emailErrorBody")
//                    _error.postValue("Error: ${emailResponse.code()} - $emailErrorBody")
//                }
//
//            } catch (e: Exception) {
//                Timber.e(e, "Exception occurred: ${e.message}")
//                _error.postValue("Exception: ${e.message}")
//            }
//        }
//    }
//}


//class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {
//
//    val emailResponse: MutableLiveData<String> = MutableLiveData()
//    val otpResponse: MutableLiveData<String> = MutableLiveData()
//    val error: MutableLiveData<String> = MutableLiveData()
//
//    fun sendEmail(email: String) {
//        viewModelScope.launch {
//            try {
//                val response = authRepository.sendPasswordResetEmail(email)
//                if (response.isSuccessful) {
//                    emailResponse.postValue(response.body()?.message)  // Assuming the response has a 'message' field
//                } else {
//                    error.postValue("Failed to send email")
//                }
//            } catch (e: Exception) {
//                error.postValue(e.message ?: "An error occurred")
//            }
//        }
//    }
//
//    fun sendOtp(email: String) {
//        viewModelScope.launch {
//            try {
//                val response = authRepository.verifyOtp(email)
//                if (response.isSuccessful) {
//                    otpResponse.postValue(response.body()?.message)  // Assuming the response has a 'message' field
//                } else {
//                    error.postValue("Failed to send OTP")
//                }
//            } catch (e: Exception) {
//                error.postValue(e.message ?: "An error occurred")
//            }
//        }
//    }
//}


class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {

    val emailResponse: MutableLiveData<String> = MutableLiveData()
    val otpResponse: MutableLiveData<String> = MutableLiveData()
    val error: MutableLiveData<String> = MutableLiveData()

    fun sendEmail(email: String) {
        viewModelScope.launch {
            try {
                val emailRequest = EmailRequest(emailid = email)
                val response = authRepository.sendPasswordResetEmail(emailRequest)
                if (response.isSuccessful) {
                    emailResponse.postValue(response.body())  // Get the string directly
                } else {
                    error.postValue("Failed to send email")
                }
            } catch (e: Exception) {
                error.postValue(e.message ?: "An error occurred")
            }
        }
    }

    fun sendOtp(email: String) {
        viewModelScope.launch {
            try {
                val otpRequest = OtpRequest(emailid = email)
                val response = authRepository.verifyOtp(otpRequest)
                if (response.isSuccessful) {
                    otpResponse.postValue(response.body())  // Get the string directly
                } else {
                    error.postValue("Failed to send OTP")
                }
            } catch (e: Exception) {
                error.postValue(e.message ?: "An error occurred")
            }
        }
    }
}


