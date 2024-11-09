package com.eapro.testingadv

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView

import androidx.appcompat.widget.Toolbar // Ensure this import




import android.view.LayoutInflater


class CustomToolbar : Toolbar {

    private lateinit var serialNumberTextView: TextView // Reference to the TextView to display the serial number

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    private fun init(context: Context) {
        // Inflate the custom layout
        LayoutInflater.from(context).inflate(R.layout.custom_toolbar, this, true)
        serialNumberTextView = findViewById(R.id.mytext) // Ensure this ID matches your layout

        // Set the serial number from SharedPreferences
        val currentSerialNumber = SerialNumberManager.getCurrentSerialNumber(context)  // updated from serial number  manager
        println("currentSerialNumber $currentSerialNumber")
        updateSerialNumber(currentSerialNumber ?: "testing0013") // Default value if null
    }




    // Method to update the serial number in the toolbar
    fun updateSerialNumber(serialNumber: String) {
        serialNumberTextView.text = serialNumber // Update the TextView with the new serial number
    }
}
