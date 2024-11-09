package com.eapro.testingadv

import retrofit2.Response


//class AuthRepository(private val apiService: ApiService) {
//
//
//    companion object {
//        private const val TAG = "AuthRepository"
//    }
//
//    suspend fun sendMail(email: Map<String, String>): Response<String> {
//        return apiService.sendMail(email)
//    }
//}


//
//class AuthRepository(private val apiService: ApiService) {
//
//    suspend fun sendMail(email: Map<String, String>): Response<String> {
//        return apiService.sendMail(email)
//    }
//
//    suspend fun verifyOtp(otpData: Map<String, String>): Response<String> {
//        return apiService.verifyOtp(otpData) // Add OTP API call here
//    }
//}
class AuthRepository(private val api: ApiService) {

    suspend fun sendPasswordResetEmail(emailRequest: EmailRequest): Response<String> {
        return api.sendPasswordResetEmail(emailRequest)
    }

    suspend fun verifyOtp(otpRequest: OtpRequest): Response<String> {
        return api.verifyOtp(otpRequest)
    }
}

