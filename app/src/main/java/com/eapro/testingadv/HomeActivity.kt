package com.eapro.testingadv
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle


import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import androidx.cardview.widget.CardView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.android.material.navigation.NavigationView
import timber.log.Timber

//
//class HomeActivity : AppCompatActivity() {
//
//
//    private lateinit var slSpinner: Spinner
//    private lateinit var viewModel: DashboardViewModel  // use this
//    private lateinit var chngswitchViewmodel: ChangeSwitchViewModel  // ups / normal
//    private lateinit var tvBattery: TextView
//    private lateinit var toggle_uppdates: TextView
//    private lateinit var tvBatteryMode: TextView
//    private lateinit var tvUpsInVoltage: TextView
//    private lateinit var tvUpsOpVoltage: TextView
//    private lateinit var tvLoadPer: TextView
//    private lateinit var solarLoadCurrent:TextView
//    private lateinit var test_solar:TextView
//    private lateinit var tvsolarCurrentCapacity: TextView
//    private lateinit var tvSolarLoadCurrent: TextView
//    private lateinit var btntoogleHighLow: Switch
//    private lateinit var tvInvAlert: TextView
//    private var currentSerialNumber: String? = null  // imp here
//
//    @SuppressLint("TimberArgCount", "MissingInflatedId")
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_home)
//
//        // Initialize views
//        slSpinner = findViewById(R.id.slSpinner)
//        tvBattery = findViewById(R.id.battry_v)
//        tvBatteryMode = findViewById(R.id.battery_mode)
//        tvLoadPer = findViewById(R.id.loadper)
//        solarLoadCurrent= findViewById(R.id.solar_slc)
//        test_solar = findViewById(R.id.test_solar)
//        tvUpsInVoltage = findViewById(R.id.input_v)
//        tvUpsOpVoltage = findViewById(R.id.output_v)
//        tvsolarCurrentCapacity = findViewById(R.id.solar_cc)
//        tvSolarLoadCurrent = findViewById(R.id.solar_kwh)
//        tvInvAlert = findViewById(R.id.Inv_alerts)
//        btntoogleHighLow = findViewById(R.id.mode_switch)  // ups / normal
//        toggle_uppdates = findViewById(R.id.toggle_uppdates)
//
//
//        val sharedPref = getSharedPreferences("UserPrefs", MODE_PRIVATE)
//        currentSerialNumber = sharedPref.getString("loggedInUserSerialNumber", null)
//
//        if (currentSerialNumber == null) {
//            Toast.makeText(this, "Serial number not found", Toast.LENGTH_SHORT).show()
//            return
//        }
//
//        // Initialize ViewModels
//        val repository = DashboardRepository(RetrofitInstance.api)
//        val factory = DashboardViewModelFactory(repository)
//        viewModel = ViewModelProvider(this, factory).get(DashboardViewModel::class.java)
//
//        val ChangeswitchRepository = ChangeSwitchRepository(RetrofitInstance.api)
//        val changeswitchFactory = ChangeSwitchViewModelFactory(ChangeswitchRepository)
//        chngswitchViewmodel = ViewModelProvider(this, changeswitchFactory).get(ChangeSwitchViewModel::class.java)
//
//        // Fetch dashboard data using the retrieved serial number
//        val payload = DashboardPayload(deviceslno = currentSerialNumber!!)
//        viewModel.fetchDashboardData(payload)
//
//        // senf from here
//        val sharedPrefsbtn = getSharedPreferences("toggle_state_prefs", MODE_PRIVATE)
//        val toggleState = sharedPrefsbtn.getBoolean("toggle_state", false) // Default to 'false' (OFF)
//
//        Log.d("ToggleStates", "Initial toggle state loaded: $toggleState") // Log initial state
//
//// Set the initial state of the switch and text based on the saved state
//        btntoogleHighLow.isChecked = toggleState
//        toggle_uppdates.text = if (toggleState) "Ups" else "Normal"
//        btntoogleHighLow.text = if (toggleState) "Ups" else "Normal"
//
//// Now, set up the listener for any future changes
//        btntoogleHighLow.setOnCheckedChangeListener { _, isChecked ->
//            val changedevicestatus = deviceswitch(switchmode = isChecked, deviceslno = currentSerialNumber)
//            chngswitchViewmodel.sendDeviceChangeStatus(changedevicestatus)
//
//            // Save the state in SharedPreferences
//            sharedPrefsbtn.edit().putBoolean("toggle_state", isChecked).apply()
//
//            // Log state change
//            Log.d("ToggleState", "Toggle state changed: $isChecked")
//
//            // Update the text based on the toggle state
//            if (isChecked) {
//                btntoogleHighLow.text = "Ups"
//                toggle_uppdates.text = "Ups"
//                Log.d("upsmode", "Switch ups mode for serial: $currentSerialNumber")
//            } else {
//                btntoogleHighLow.text = "Normal"
//                toggle_uppdates.text = "Normal"
//                Log.d("normal", "Switch normal mode for serial: $currentSerialNumber")
//            }
//        }
//
//        // Observe dashboard data and update cards
//        viewModel.dashboardData.observe(this, Observer { batteryItems ->
//            if (batteryItems != null) {
//                updateCards(batteryItems)
//            }
//        })
//
//
//        chngswitchViewmodel.apiResponse.observe(this, Observer { response -> // owner of life cycle is here
//            if (response.RESPONSE == "Success") {
//                Timber.tag("databutton").d("ups /normal  Switch changed successfully.")
//                } else {
//                Timber.tag("HomeActivity").d("Failed to change switch: %s", response.RESPONSE)
//                }
//            })
//    }
//
//    private fun updateCards(batteryList: List<InverterData>) {
//        if (batteryList.isNotEmpty()) {
//            // Set battery voltage for the first item
//            tvBattery.text = batteryList[0].batteryVolt
//
//            // Check if batteryList has enough data to update other fields
//            if (batteryList.size >= 18) {
//                Log.d("battery_data ","your all dynamic data is here"+batteryList.size)
//
//                batteryList.forEachIndexed { index, inverterData ->
//                    Log.d("saradata", "Index: $index, Inverter Data: $inverterData")
//                }
//                // Mapping data from batteryList to UI elements
//                tvBatteryMode.text = batteryList[0].chargingMode
//                tvUpsInVoltage.text = batteryList[0].ipVolt
//                tvUpsOpVoltage.text = batteryList[0].opVolt
//                tvLoadPer.text = batteryList[0].load
//                tvSolarLoadCurrent.text = batteryList[0].solarKWH
//                tvInvAlert.text = batteryList[0].alerts
//                btntoogleHighLow.isChecked = batteryList[0].chargingMode == "true"
//
//                // Example: Create dummy solarKWH values for each day (for testing)
//
//            }
//        }
//    }
//}

class HomeActivity : AppCompatActivity() {


    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle

    private lateinit var slSpinner: Spinner
    private lateinit var viewModel: DashboardViewModel
    private lateinit var chngswitchViewmodel: ChangeSwitchViewModel
    private lateinit var tvBattery: TextView
    private lateinit var toggle_uppdates: TextView
    private lateinit var tvBatteryMode: TextView
    private lateinit var tvUpsInVoltage: TextView
    private lateinit var tvUpsOpVoltage: TextView
    private lateinit var tvLoadPer: TextView
    private lateinit var solarLoadCurrent: TextView
    private lateinit var test_solar: TextView
    private lateinit var tvsolarCurrentCapacity: TextView
    private lateinit var tvSolarLoadCurrent: TextView
    private lateinit var btntoogleHighLow: Switch
    private lateinit var tvInvAlert: TextView
    private var currentSerialNumber: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        drawerLayout = findViewById(R.id.drawerLayout)
        val navigationView: NavigationView = findViewById(R.id.navigationView)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open_drawer, R.string.close_drawer)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    // Move to HomeActivity
                    val intent = Intent(this, DashboardActivity::class.java)
                    startActivity(intent)
                }

                R.id.nav_profile -> {

                    val intent = Intent(this, DataActivity::class.java)
                    startActivity(intent)
                }
                R.id.nav_setting -> {

                    val intent = Intent(this, AlertActivity::class.java)
                    startActivity(intent)
                }


            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true


        }

// navigation end here
        initializeViews()
        initializeViewModels()
        setupToggleSwitch()

        val sharedPref = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        currentSerialNumber = sharedPref.getString("loggedInUserSerialNumber", null)

        if (currentSerialNumber == null) {
            Toast.makeText(this, "Serial number not found", Toast.LENGTH_SHORT).show()
            return
        }

        viewModel.fetchDashboardData(DashboardPayload(deviceslno = currentSerialNumber!!))

        viewModel.dashboardData.observe(this, Observer { batteryItems ->
            if (batteryItems != null) {
                updateCards(batteryItems)
            }
        })

        chngswitchViewmodel.apiResponse.observe(this, Observer { response ->

            if (response.RESPONSE == "Success") {
                Timber.d("Switch changed successfully.")
                Log.d("HomeActivity", "Switch status updated successfully on server.")
            } else {
                Timber.d("Failed to change switch: ${response.RESPONSE}")
            }
        })

        chngswitchViewmodel.toggleState.observe(this, Observer { isChecked ->
            btntoogleHighLow.isChecked = isChecked
            updateToggleText(isChecked)
        })

        chngswitchViewmodel.error.observe(this, Observer { errorMessage ->
            Toast.makeText(this, "Error: $errorMessage", Toast.LENGTH_SHORT).show()
        })
    }

    private fun initializeViews() {
        slSpinner = findViewById(R.id.slSpinner)
        tvBattery = findViewById(R.id.battry_v)
        tvBatteryMode = findViewById(R.id.battery_mode)
        tvLoadPer = findViewById(R.id.loadper)
        solarLoadCurrent = findViewById(R.id.solar_slc)
        test_solar = findViewById(R.id.test_solar)
        tvUpsInVoltage = findViewById(R.id.input_v)
        tvUpsOpVoltage = findViewById(R.id.output_v)
        tvsolarCurrentCapacity = findViewById(R.id.solar_cc)
        tvSolarLoadCurrent = findViewById(R.id.solar_kwh)
        tvInvAlert = findViewById(R.id.Inv_alerts)
        btntoogleHighLow = findViewById(R.id.mode_switch)
        toggle_uppdates = findViewById(R.id.toggle_uppdates)
    }

    private fun initializeViewModels() {
        val dashboardRepo = DashboardRepository(RetrofitInstance.api)
        viewModel = ViewModelProvider(this, DashboardViewModelFactory(dashboardRepo)).get(DashboardViewModel::class.java)

        val changeSwitchRepo = ChangeSwitchRepository(RetrofitInstance.api)
        chngswitchViewmodel = ViewModelProvider(this, ChangeSwitchViewModelFactory(changeSwitchRepo)).get(ChangeSwitchViewModel::class.java)
    }

    private fun setupToggleSwitch() {
        val sharedPrefsbtn = getSharedPreferences("toggle_state_prefs", MODE_PRIVATE)
        val savedToggleState = sharedPrefsbtn.getBoolean("toggle_state", false)

        btntoogleHighLow.isChecked = savedToggleState
        updateToggleText(savedToggleState)

        btntoogleHighLow.setOnCheckedChangeListener { _, isChecked ->
            val changedevicestatus = deviceswitch(switchmode = isChecked, deviceslno = currentSerialNumber)
            chngswitchViewmodel.sendDeviceChangeStatus(changedevicestatus)
            Log.d("HomeActivity", "Toggle state saved to SharedPreferences: $isChecked")

            sharedPrefsbtn.edit().putBoolean("toggle_state", isChecked).apply()
            updateToggleText(isChecked)
        }
    }

    private fun updateToggleText(isChecked: Boolean) {
        btntoogleHighLow.text = if (isChecked) "Ups" else "Normal"
        toggle_uppdates.text = btntoogleHighLow.text
    }

    private fun updateCards(batteryList: List<InverterData>) {
        if (batteryList.isNotEmpty()) {
            tvBattery.text = batteryList[0].batteryVolt
            if (batteryList.size >= 18) {
                Log.d("battery_data", "Dynamic data size: ${batteryList.size}")

                batteryList.forEachIndexed { index, inverterData ->
                    Log.d("saradata", "Index: $index, Inverter Data: $inverterData")
                }
                tvBatteryMode.text = batteryList[0].chargingMode
                tvUpsInVoltage.text = batteryList[0].ipVolt
                tvUpsOpVoltage.text = batteryList[0].opVolt
                tvLoadPer.text = batteryList[0].load
                tvSolarLoadCurrent.text = batteryList[0].solarKWH
                tvInvAlert.text = batteryList[0].alerts
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                toggle.onOptionsItemSelected(item)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}




