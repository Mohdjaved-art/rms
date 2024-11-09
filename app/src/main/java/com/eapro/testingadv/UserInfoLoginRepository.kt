package com.eapro.testingadv

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UserInfoLoginRepository(private val apiService: ApiService) {

    fun getUserInfo(onResult: (Result<List<UserInfoResponse>>) -> Unit) {
        val call = apiService.getUserInfo()

        call.enqueue(object : Callback<List<UserInfoResponse>> {
            override fun onResponse(
                call: Call<List<UserInfoResponse>>,
                response: Response<List<UserInfoResponse>>
            ) {
                if (response.isSuccessful) {
                    val userInfo = response.body()
                    if (userInfo != null) {
                        onResult(Result.success(userInfo))
                    } else {
                        onResult(Result.failure(Throwable("No data found")))
                    }
                } else {
                    onResult(Result.failure(Throwable("Error fetching user info")))
                }
            }

            override fun onFailure(call: Call<List<UserInfoResponse>>, t: Throwable) {
                onResult(Result.failure(t))
            }
        })
    }
}


