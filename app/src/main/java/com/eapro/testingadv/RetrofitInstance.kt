package com.eapro.testingadv
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
//
object RetrofitInstance {
    private const val BASE_URL = "https://mysolarpower.net/"

    val api: ApiService by lazy {
        val gson = GsonBuilder().setLenient().create()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService::class.java)
    }
}
//object RetrofitInstance {
//    private const val BASE_URL = "https://mysolarpower.net/"
// here we are doing this for the purpose of lazy init
//
//
//
//    val api: ApiService by lazy {
//        val gson = GsonBuilder().setLenient().create()
// here we have an base url for this and from here we fetch the api req and api response
// this is thumb rule and can be done everytime when fetching the api
// if they build team destroy all of them
// if you never say think it's your mistake not there
// don't tolerate even a single dis-respect from them
// respect come when you earn above 1 lac
// so made a millions of mistake
//
//        Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create(gson))
//            .client(
//                OkHttpClient.Builder()
//                .connectTimeout(30, TimeUnit.SECONDS)// time out after this time
//                .readTimeout(30, TimeUnit.SECONDS) // for waiting to reading data from the server
//                .writeTimeout(30, TimeUnit.SECONDS)
//                .build()) // Add custom timeout configuration
//            .build()
//            .create(ApiService::class.java)
//    }
//}
