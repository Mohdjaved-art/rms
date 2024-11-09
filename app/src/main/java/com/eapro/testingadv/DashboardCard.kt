package com.eapro.testingadv



data class DashboardCard(
    val title: String,
    val imageResId: Int,
    val targetActivity: Class<*>,
//    val serialNumber: String? = "GS19183100004DCB"
    val serialNumber: String? = null // Add this property to hold the serial number
)
