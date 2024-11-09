package com.eapro.testingadv
import com.google.gson.annotations.SerializedName

data class InverterData(
    @SerializedName("Date") val date: String?,
    @SerializedName("Time") val time: String?,
    @SerializedName("Serial No") val serialNo: String?,
    @SerializedName("Inverter Capacity") val inverterCapacity: String?,
    @SerializedName("I/P Volt") val ipVolt: String?,
    @SerializedName("O/P Volt") val opVolt: String?,
    @SerializedName("Battery Volt") val batteryVolt: String?,
    @SerializedName("Charging Current") val chargingCurrent: String?,
    @SerializedName("Load %") val load: String?,

    @SerializedName("Solar Current Capacity") val solarCurrentCapacity: String?,
    @SerializedName("InverterDischargingcur") val inverterDischargingcur: String?,

    @SerializedName("Solar KWH") val solarKWH: String?,
    @SerializedName("ON/OFF") val onOff: String?,
    @SerializedName("Charging Mode") val chargingMode: String?,
    @SerializedName("Saving Level") val savingLevel: String?,
    @SerializedName("Mode") val mode: String?,
    @SerializedName("Alerts") val alerts: String?,
    @SerializedName("Inverter Display Time") val inverterDisplayTime: String?
)

//data class inverter(
//    val Alerts: String,
//    val Battery Volt: String,
//    val Charging Current: String, //
//    val Charging Mode: String,
//    val Date: String,
//    val I/P
//    Volt: String,
//    val Inverter Capacity: String,  // not there
//    val Inverter Display
//    Time: String,
//    val InverterDischargingcur: String,
//    val Load %: String,
//    val Mode: String,
//    val O/P Volt: String,
//    val ON/OFF: String,
//    val Saving
//    Level: String,
//    val Serial No: String,
//    val Solar Current
//    Capacity: String,
//    val Solar KWH: String,
//    val Time: String
//)
