package com.eapro.testingadv


import com.google.gson.annotations.SerializedName

data class  UserInfoResponse(
    val rowid: Int,
    val userid: String,
    val name: String,
    val admin: String,

    @SerializedName("Email Id")
    val emailId: String,

    @SerializedName("Mobile No")
    val mobileNo: String,

    @SerializedName("Inverter Name")
    val inverterName: String,

    @SerializedName("Inverter Serial No")
    val inverterSerialNo: String,

    val status: String,
    val regdate: String
)

