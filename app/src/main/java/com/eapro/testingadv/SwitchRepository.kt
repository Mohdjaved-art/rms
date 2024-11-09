package com.eapro.testingadv

import android.annotation.SuppressLint
import timber.log.Timber

//class SwitchRepository(private val apiService: ApiService) {
//
//    @SuppressLint("TimberArgCount")
//    suspend fun sendDeviceStatus(deviceStatus: DeviceStatus): ApiResponse {
//        // Send the request and log the device status being sent
//        Timber.tag("SwitchRepository")
//            .d(
//                "%s%s",
//                "%s, deviceSlNo=",
//                "Sending device status: charging=%s",
//                deviceStatus.onoff,
//                deviceStatus.deviceslno
//            )
//
//        val response = apiService.changeSwitch(deviceStatus)
//
//        // Log the response received from the server
//        Timber.tag("useridhai")
//            .d("%s%s", "%s, MESSAGE=", "%s%s", "%s, RESPONSE=", "Received response: USERID=%s", response.USERID, response.RESPONSE, response.MESSAGE)
//
//        return response
//    }
//}

class ChangeSwitchRepository(private val apiService: ApiService) {

    @SuppressLint("TimberArgCount")
    suspend fun sendDeviceChangeStatus(changedeviceStatus: deviceswitch): ApiResponse {
        // Send the request and log the device status being sent
        Timber.tag("SwitchRepository")
            .d(
                "%s%s",
                "%s, deviceSlNo=",
                "Sending device status: charging=%s",
                changedeviceStatus.switchmode,
                changedeviceStatus.deviceslno
            )

        val response = apiService.sendDeviceChangeStatus(changedeviceStatus)

        // Log the response received from the server
        Timber.tag("useridhai")
            .d("%s%s", "%s, MESSAGE=", "%s%s", "%s, RESPONSE=", "Received response: USERID=%s", response.USERID, response.RESPONSE, response.MESSAGE)

        return response
    }
}

//class LowSwitchRepository(private val apiService: ApiService) {
//
//    @SuppressLint("TimberArgCount")
//    suspend fun sendDeviceLowStatus(changeLowStatus: Lowswitch): ApiResponse {
//        // Send the request and log the device status being sent
//        Timber.tag("SwitchRepository")
//            .d(
//                "%s%s",
//                "%s, deviceSlNo=",
//                "Sending device status: charging=%s",
//                changeLowStatus.charging,
//                changeLowStatus.deviceslno
//            )
//
//        val response = apiService.sendDeviceLowStatus(changeLowStatus)
//
//        // Log the response received from the server
//        Timber.tag("useridhai")
//            .d("%s%s", "%s, MESSAGE=", "%s%s", "%s, RESPONSE=", "Received response: USERID=%s", response.USERID, response.RESPONSE, response.MESSAGE)
//
//        return response
//    }
//}
