package com.eapro.testingadv
import com.google.gson.annotations.SerializedName



data class LoginResponse(
    val DATA: List<UserData>,  // Assuming your response contains a "DATA" array
    val RESPONSE: String,      // Assuming the response contains a "RESPONSE" field
    val MESSAGE: String?       // "MESSAGE" can be null or a string
)

data class UserData(
    val rowid: Int,
    val created_on: String,
    val isactive: Boolean,
    val userid: String,
    val firstname: String,
    val lastname: String,
    val emailaddress: String,
    val regdate: String,
    val mobile: String,
    val altmobile: String,
    val address: String,
    val UserType: String,
    val parentid: Int,
    val sitelogo: String,
    val NoOfUsers: Int?,
    val UploadBanner: String,
    val UploadBanners: String
)
