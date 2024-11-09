package com.eapro.testingadv


import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import android.util.Log
import timber.log.Timber

//
class LoginRepository {


    // use retrofit instance here

    fun loginUser(username: String, password: String, callback: (LoginResponse?) -> Unit) {


        val request = LoginRequest(username, password)


        val call = RetrofitInstance.api.loginUser(request)  // without hilt it work


        call.enqueue(object : Callback<LoginResponse> {

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                // Log the response code
                Timber.tag("LoginRepository").d("Response Code: %s", response.code())

                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    Timber.tag("LoginRepository").d("Success Response: %s", loginResponse)
                    callback(loginResponse)
                } else {
                    // Log the error body and response code for debugging
                    val errorBody = response.errorBody()?.string()
                    Timber.tag("LoginRepository").d("Error Response Code: %s", response.code())

                    Timber.tag("LoginRepository").d("Non-JSON Error Response: %s", errorBody)
                    callback(null)
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Timber.tag("LoginRepository").e("Network Failure: %s", t.message)
                callback(null)
            }
        })
    }
}



