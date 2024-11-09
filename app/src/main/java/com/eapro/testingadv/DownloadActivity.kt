package com.eapro.testingadv
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.app.DatePickerDialog
import android.content.Context
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

class DownloadActivity : AppCompatActivity() {
   private lateinit var progressBarDownloading:ProgressBar
    private lateinit var viewModel: CustomerDataViewModel
    private lateinit var adapter: CustomerDataAdapter
    private lateinit var repository: CustomerDataRepository

    private val TAG = "DownloadActivity"
    private lateinit var fromDateTextView: TextView
    private lateinit var toDateTextView: TextView
    private var selectedFromDate: String? = null
    private var selectedToDate: String? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_download)

        // Initialize repository and ViewModel
        repository = CustomerDataRepository(RetrofitInstance.api)
        val factory = CustomerDataViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(CustomerDataViewModel::class.java)

        progressBarDownloading = findViewById(R.id.progressBarDownloading)

        // Set up RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewCustomerData)
        recyclerView.layoutManager = LinearLayoutManager(this)
            // here we call the recycle view holder to manage the linear layout

        // Initialize UI components
        fromDateTextView = findViewById(R.id.tvFromDate)
        toDateTextView = findViewById(R.id.tvToDate)
        val downloadButton = findViewById<Button>(R.id.download_report)

        // Fetch the logged-in user's serial number from SharedPreferences
        val sharedPref = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val loggedInUserSerialNumber = sharedPref.getString("loggedInUserSerialNumber", null)

        // Set OnClickListener for From Date TextView
        fromDateTextView.setOnClickListener {
            showDatePicker { date ->
                selectedFromDate = date
                fromDateTextView.text = selectedFromDate // Update UI with selected date
            }
        }

        // Set OnClickListener for To Date TextView
        toDateTextView.setOnClickListener {
            showDatePicker { date ->
                selectedToDate = date
                toDateTextView.text = selectedToDate // Update UI with selected date
            }
        }


        downloadButton.setOnClickListener {
            progressBarDownloading.visibility = View.VISIBLE
            if (loggedInUserSerialNumber != null && selectedFromDate != null && selectedToDate != null) {
                // Fetch customer data using the dynamic serial number and selected dates
                viewModel.fetchCustomerData(CustomerDownloadData(
                    deviceslno = loggedInUserSerialNumber,
                    date = selectedFromDate!!,
                    date2 = selectedToDate!!
                ))

                viewModel.customerData.observe(this, Observer { data ->
                    if (data != null) {
                        adapter = CustomerDataAdapter(data)
                        recyclerView.adapter = adapter
                        Log.d(TAG, "Data fetched successfully, number of records: ${data.size}")

                       // Show when downloading starts
                        progressBarDownloading.visibility = View.GONE     // Hide when downloading finishes

                        // Create Excel file once the data is fetched
                        saveDataToExcel(data)
                    } else {
                        // Handle empty or error case
                        Toast.makeText(this, "No data available", Toast.LENGTH_SHORT).show()
                        Log.w(TAG, "No data available to save.")
                    }
                })
            } else {
                // Handle case where serial number or dates are not available
                Toast.makeText(this, "Please select valid dates and ensure you are logged in.", Toast.LENGTH_SHORT).show()
            }
        }

    }

    // Show DatePicker Dialog
    private fun showDatePicker(onDateSelected: (String) -> Unit) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            val date = String.format("%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay)
            onDateSelected(date) // Return selected date to the callback
        }, year, month, day)

        datePickerDialog.show()
    }

    // Generate and save the Excel file
    private fun saveDataToExcel(data: List<InverterData>) {
        val workbook: Workbook = XSSFWorkbook()
        val sheet = workbook.createSheet("CustomerData")

        // Add header row
        val headerRow = sheet.createRow(0)
        headerRow.createCell(0).setCellValue("Date")
        headerRow.createCell(1).setCellValue("Time")
        headerRow.createCell(2).setCellValue("serialNo")
        headerRow.createCell(3).setCellValue("inverterCapacity")
        headerRow.createCell(4).setCellValue("ipVolt")
        headerRow.createCell(5).setCellValue("opVolt")
        headerRow.createCell(6).setCellValue("batteryVolt")
        headerRow.createCell(7).setCellValue("chargingCurrent")
        headerRow.createCell(8).setCellValue("load")
        headerRow.createCell(9).setCellValue("solarCurrentCapacity")
        headerRow.createCell(10).setCellValue("solarKWH")
        headerRow.createCell(11).setCellValue("onOff")
        headerRow.createCell(12).setCellValue("chargingMode")
        headerRow.createCell(13).setCellValue("savingLevel")
        headerRow.createCell(14).setCellValue("alerts")
        headerRow.createCell(15).setCellValue("inverterDisplayTime")



        // Add data rows
        for (i in data.indices) {
            val row = sheet.createRow(i + 1)

            row.createCell(0).setCellValue(data[i].date ?: "N/A")
            row.createCell(1).setCellValue(data[i].time ?: "N/A")
            row.createCell(2).setCellValue(data[i].serialNo ?: "N/A")
            row.createCell(3).setCellValue(data[i].inverterCapacity ?: "N/A")

            // Convert numeric fields to doubles to store as numbers in Excel
            row.createCell(4).setCellValue(data[i].ipVolt?.toDoubleOrNull() ?: 0.0)
            row.createCell(5).setCellValue(data[i].opVolt?.toDoubleOrNull() ?: 0.0)
            row.createCell(6).setCellValue(data[i].batteryVolt?.toDoubleOrNull() ?: 0.0)
            row.createCell(7).setCellValue(data[i].chargingCurrent?.toDoubleOrNull() ?: 0.0)
            row.createCell(8).setCellValue(data[i].load?.toDoubleOrNull() ?: 0.0)
            row.createCell(9).setCellValue(data[i].solarCurrentCapacity?.toDoubleOrNull() ?: 0.0)
            row.createCell(10).setCellValue(data[i].solarKWH?.toDoubleOrNull() ?: 0.0)

            row.createCell(11).setCellValue(data[i].onOff ?: "N/A")
            row.createCell(12).setCellValue(data[i].chargingMode ?: "N/A")
            row.createCell(13).setCellValue(data[i].savingLevel ?: "N/A")
            row.createCell(14).setCellValue(data[i].alerts ?: "N/A")
            row.createCell(15).setCellValue(data[i].inverterDisplayTime ?: "N/A")

            // Log each added row
            Log.d("addedrow", "Added row ${i + 1}: ${data[i]}")
        }

        // Save file to the Downloads directory
        val fileName = "CustomerData.xlsx"
        val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val file = File(downloadsDir, fileName)
        val fileOut = FileOutputStream(file)
        workbook.write(fileOut)
        fileOut.close()
        workbook.close()

        try {

            Log.d(TAG, "Excel file saved at: ${file.absolutePath}")
            Toast.makeText(this, "Excel file saved at: ${file.absolutePath}", Toast.LENGTH_LONG).show()
        } catch (e: IOException) {
            e.printStackTrace()
            Log.e(TAG, "Failed to save Excel file", e)
            Toast.makeText(this, "Failed to save Excel file", Toast.LENGTH_SHORT).show()
        }
    }
}









