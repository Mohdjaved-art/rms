package com.eapro.testingadv

import javax.inject.Inject
import javax.inject.Singleton


class AlertRepository(private val apiService: ApiService) {

    suspend fun getAlerts(deviceslno: String): List<AlertResponse>? {
        // Make the network request using the provided apiService
        val response = apiService.getAlerts(AlertRequest(deviceslno, "Alert"))
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }
}

