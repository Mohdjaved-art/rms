package com.eapro.testingadv

import com.google.gson.annotations.SerializedName


data class AlertRequest(
    val deviceslno: String,
    val flag: String
)

data class AlertResponse(
    @SerializedName("Serial No") val serialNo: Int,
    @SerializedName("Inverter Alert") val inverterAlert: String,
    val Date: String,
    val Time: String
)