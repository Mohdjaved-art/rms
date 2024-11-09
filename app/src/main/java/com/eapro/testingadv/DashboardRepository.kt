package com.eapro.testingadv

import android.util.Log
import timber.log.Timber


class DashboardRepository(private val apiService: ApiService) {

    suspend fun getDashboardData(payload: DashboardPayload): List<InverterData>? {
        return try {
            val response = apiService.getDashboardData(payload)

            // Log the raw response
            Timber.tag("DashboardRepository").d("Raw API Response: %s", response.body())

            if (response.isSuccessful) {
                val data = response.body()
                Timber.tag("DashboardRepository").d("API Response Data: %s", data)
                data
            } else {
                Timber.tag("Dashboarderror").e("API Error: %s", response.errorBody()?.string())
                null
            }
        } catch (e: Exception) {
            Timber.tag("othermessage").e("Network or Parsing Error: %s", e.message)
            null
        }
    }
}



