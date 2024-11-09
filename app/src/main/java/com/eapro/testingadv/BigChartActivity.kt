package com.eapro.testingadv

import android.annotation.SuppressLint
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener


class BigChartActivity : AppCompatActivity() {

    private lateinit var barChart: BarChart

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        // Step 1: Get reference to BarChart in the layout
        barChart = findViewById(R.id.bigBarChart)

        // Step 2: Set up BarChart data
        val entries = mutableListOf<BarEntry>()
        entries.add(BarEntry(0f, 100f))  // Example entry for Sunday
        entries.add(BarEntry(1f, 120f))  // Example entry for Monday
        // Add more data for the week (Sunday to Saturday)

        val barDataSet = BarDataSet(entries, "Weekly Data")
        val barData = BarData(barDataSet)

        // Step 3: Set data to the chart and refresh
        barChart.data = barData
        barChart.invalidate()

        // Step 4: Set up click listener for the BarChart
        barChart.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
            override fun onValueSelected(e: Entry?, h: Highlight?) {
                if (e != null) {
                    val selectedDay = e.x.toInt() // Get the index of the clicked bar (X-axis)
                    val value = e.y // Get the Y-axis value (solar KWH)

                    // Open big view or show detailed information
                    openBigView(selectedDay, value)
                }
            }

            override fun onNothingSelected() {
                // Handle case where no bar is selected
            }
        })
    }

    // Function to handle showing a big view
    private fun openBigView(dayIndex: Int, value: Float) {
        // Open a dialog or new activity with the details for the selected day
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_big_chart)
        dialog.show()

    }
}
