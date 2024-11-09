package com.eapro.testingadv

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber



class DashboardActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var dashboardAdapter: DashboardAdapter // For RecyclerView
    private lateinit var viewModel: UserInfoLoginViewModel
    private var loggedInUserId: String? = null
    private lateinit var customToolbar: CustomToolbar // Use custom toolbar
    private var currentSerialNumber: String? = null // Variable to hold the current serial number
    private lateinit var serialNumberSpinner: Spinner // Reference to the Spinner
    private lateinit var serialNumbers: List<String> // List to hold available serial numbers

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val logoutTextView: TextView = findViewById(R.id.logout)

        // Set the click listener for logout
        logoutTextView.setOnClickListener {
            // Clear the user session
            val sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.clear()  // Clears all saved preferences, including login status
            editor.apply()

            // Navigate back to LoginActivity
            val intent = Intent(this@DashboardActivity, MainActivity::class.java)
            intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // Clear the activity stack
            startActivity(intent)

            // Optionally, show a logout confirmation
            Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show()

            // Finish the current activity
            finish()
        }

        // Initialize the custom toolbar
        customToolbar = findViewById(R.id.customToolbar) // Match this with the new ID
        setSupportActionBar(customToolbar) // Set the custom toolbar as the action bar

        // Initialize the serial number spinner
        serialNumberSpinner = findViewById(R.id.customespinner) // Reference to your Spinner

        // Set up the default serial numbers (including the default one)
        serialNumbers = listOf("testing0013", "serialNumber1", "serialNumber2", "serialNumber3") // Example serial numbers
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, serialNumbers)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        serialNumberSpinner.adapter = adapter

        // Set the default selection
        serialNumberSpinner.setSelection(0) // Set default to "testing0013"

//        serialNumberSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(
//                parent: AdapterView<*>,
//                view: View,
//                position: Int,
//                id: Long
//            ) {
//                val selectedSerialNumber = parent.getItemAtPosition(position).toString()
//
//                customToolbar.updateSerialNumber(selectedSerialNumber)
//                // Update the toolbar with the selected serial number
//
//                saveInverterSerialNumber(selectedSerialNumber) // Save selected serial number to SharedPreferences
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>) {
//                // Do nothing
//            }
//        }

        serialNumberSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedSerialNumber = parent.getItemAtPosition(position).toString()

                // Check if view is not null
                if (view != null) {
                    customToolbar.updateSerialNumber(selectedSerialNumber)
                    // Update the toolbar with the selected serial number
                    saveInverterSerialNumber(selectedSerialNumber) // Save selected serial number to SharedPreferences
                } else {
                    // Optional: Handle the case where view is null
                    Log.e("DashboardActivity", "View is null in onItemSelected")
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }


        // Retrieve the logged-in user ID from SharedPreferences
        loggedInUserId = getLoggedInUserId()

        // Log the retrieved user ID for debugging
        Log.d("DashboardActivity", "Logged-in User ID: $loggedInUserId")

        // Initialize RecyclerView and Adapter
        setupRecyclerView()

        // Initialize ViewModel
        viewModel = ViewModelProvider(
            this,
            UserInfoLoginViewModelFactory(UserInfoLoginRepository(RetrofitInstance.api))
        ).get(UserInfoLoginViewModel::class.java)

        // Observe ViewModel's LiveData for user info
        observeUserInfo()

        // Observe ViewModel's LiveData for errors
        observeErrors()

        // Fetch user information when the activity starts
        viewModel.getUserInfo()
    }

    // Method to update the custom toolbar with the selected serial number
    private fun updateToolbarWithSerialNumber(serialNumber: String) {
        customToolbar.updateSerialNumber(serialNumber) // Update toolbar with the selected serial number
    }

    // Retrieve the logged-in user ID from SharedPreferences
    private fun getLoggedInUserId(): String? {
        val sharedPref = getSharedPreferences("app_prefs", MODE_PRIVATE)
        return sharedPref.getString("loggedInUserId", null)
    }

    // Set up the RecyclerView and its adapter
    private fun setupRecyclerView() {
        recyclerView = findViewById(R.id.recycleview)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        // Create dashboard cards
        val cards = listOf(
            DashboardCard("Testing home", R.drawable.home, HomeActivity::class.java),
            DashboardCard("Alert", R.drawable.alert, AlertActivity::class.java),
            DashboardCard("Data", R.drawable.data, DataActivity::class.java),
            DashboardCard("Inverter Setting", R.drawable.setting, InverterSettingActivity::class.java),
            DashboardCard("Download", R.drawable.downloads, DownloadActivity::class.java),
            DashboardCard("Video Tutorial", R.drawable.video_tutorial, VideoTutorialActivity::class.java),
            DashboardCard("Add New System", R.drawable.addnewsystem, AddNewSystemActivity::class.java)
        )

        // Initialize the dashboard adapter for RecyclerView
        dashboardAdapter = DashboardAdapter(cards)
        recyclerView.adapter = dashboardAdapter
    }

    // Observe ViewModel's LiveData for user info
    private fun observeUserInfo() {
        viewModel.userInfo.observe(this) { userInfoList ->
            Timber.tag("DashboardActivity").d("User Info List: %s", userInfoList)

            userInfoList.forEach { userInfo ->
                if (userInfo.userid == loggedInUserId) {
                    // Log and save the inverter serial number
                    currentSerialNumber = userInfo.inverterSerialNo
                    Timber.tag("current_serial_no")
                        .d("Inverter Serial No for $loggedInUserId: $currentSerialNumber")

                    saveInverterSerialNumber(currentSerialNumber)
                    customToolbar.updateSerialNumber(
                        currentSerialNumber ?: "Unknown"
                    ) // Update toolbar with current serial number
                }
            }
        }
    }

    // Observe ViewModel's LiveData for errors
    private fun observeErrors() {
        viewModel.errorMessage.observe(this) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(this, "Error: $it", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Save the inverter serial number to SharedPreferences
    private fun saveInverterSerialNumber(serialNumber: String?) {
        val sharedPref = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("loggedInUserSerialNumber", serialNumber)
            apply()
//            saves the changes asynchronously, meaning the changes are stored in the background without blocking the main thread. Alternatively,
//            commit() could be used, but it works synchronously and can block the main thread.
        }
    }
}







