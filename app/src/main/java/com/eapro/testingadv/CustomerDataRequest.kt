package com.eapro.testingadv
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


data class CustomerDataRequest(
    val deviceslno: String,
)

@Entity(tableName = "customer_data") // in table we have all the data
data class CustomerDataResponse(
    val Date: String,
    val Time: String,

    @PrimaryKey
    @SerializedName("Serial No")
    val serialNo: String,

    @SerializedName("Inverter Capacity")
    val inverterCapacity: String?,

    @SerializedName("I/P Volt")
    val inputVolt: String,

    @SerializedName("O/P Volt")
    val outputVolt: String,

    @SerializedName("Battery Volt")
    val batteryVolt: String,

    @SerializedName("Charging Current")
    val chargingCurrent: String,

    @SerializedName("Load %")
    val loadPercentage: String,

    @SerializedName("Solar Current Capacity")
    val solarCurrentCapacity: String,

    @SerializedName("Solar KWH")
    val solarKWH: String,

    @SerializedName("ON/OFF")
    val onOff: String,

    @SerializedName("Charging Mode")
    val chargingMode: String,

    @SerializedName("Saving Level")
    val savingLevel: String,

    @SerializedName("Mode")
    val mode: String,

    @SerializedName("Alerts")
    val alerts: String,

    @SerializedName("Inverter Display Time")
    val inverterDisplayTime: String,

    @SerializedName("InverterDischargingcur")
    val inverterDischargingCurrent: String
)
