package com.eapro.testingadv

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint


class AlertActivity : AppCompatActivity() {

    private lateinit var viewModel: AlertViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AlertAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alert)

        // Initialize RecyclerViewzz
        recyclerView = findViewById(R.id.recyclerViewAlerts)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Initialize Repository and ViewModel
        val repository = AlertRepository(RetrofitInstance.api)
        val factory = AlertViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(AlertViewModel::class.java)

        // Observe LiveData for alerts
        viewModel.alerts.observe(this, Observer { alerts ->
            alerts?.let {
                adapter = AlertAdapter(it)
                recyclerView.adapter = adapter
            }
        })

        // Observe for errors
        viewModel.error.observe(this, Observer { errorMessage ->
            errorMessage?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        })

        // Fetch alerts based on the logged-in user's serial number
        val sharedPref = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val loggedInUserSerialNumber = sharedPref.getString("loggedInUserSerialNumber", null)

        if (loggedInUserSerialNumber != null) {
            // Use the dynamically retrieved serial number to fetch alerts
            viewModel.fetchAlerts(loggedInUserSerialNumber)
        } else {
            // Handle case where serial number is not available
            Toast.makeText(this, "Serial number not found. Please log in again.", Toast.LENGTH_SHORT).show()
        }
    }
}


