package com.eapro.testingadv

import android.content.Context

object SerialNumberManager { // MAKE it singleton

    private const val PREFS_NAME="USER_SERIALNUMBER"

    private const val SERIAL_NUMBER_KEY = "current_serial_number"

    fun setCurrentSerialNumber(context: Context, serialNumber: String) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(SERIAL_NUMBER_KEY, serialNumber).apply()
    }

    fun getCurrentSerialNumber(context: Context): String? {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getString(SERIAL_NUMBER_KEY, "testing0013") // Default value
    }
}