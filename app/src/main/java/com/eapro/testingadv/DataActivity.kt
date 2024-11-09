package com.eapro.testingadv
import android.graphics.Rect
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import timber.log.Timber


class DataActivity : AppCompatActivity() {

    private lateinit var viewModel: CustomerViewModel

    private lateinit var recyclerView: RecyclerView

    private lateinit var adapter: InverterDataAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data)

        // Initialize Room database and repository
        val database = Room.databaseBuilder(
            applicationContext,
            CustomerDatabase::class.java,
            "customer_db"
        )
            .fallbackToDestructiveMigration() // Wipes database if schema changes
            .build()

        // Initialize repository
        val repository = CustomerRepository(database.customerDATAdAO(), RetrofitInstance.api)

        // Initialize ViewModel using the repository
        val factory = CustomerViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(CustomerViewModel::class.java)

        // Set up RecyclerView
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(SpaceItemDecoration(4)) // Adds spacing between items

        // Observe customer data and update RecyclerView
        viewModel.customerData.observe(this, Observer { data ->
            data?.let {
                Timber.d("Data from Room and server: $it")

                // Check if adapter is already initialized
                if (::adapter.isInitialized) {
                    adapter.updateData(it)  // Refresh the RecyclerView with new data
                } else {
                    adapter = InverterDataAdapter(it.toMutableList()) // Initialize adapter
                    recyclerView.adapter = adapter
                }
            }
        })

        // Observe error messages
        viewModel.error.observe(this, Observer { errorMessage ->
            errorMessage?.let {
                Timber.tag("CustomerDataError").e(it)
                // Show error in the UI if needed (e.g., Toast, Snackbar)
            }
        })

        // Fetch customer data based on a specific serial number
        val sharedPref = getSharedPreferences("UserPrefs", MODE_PRIVATE)

        val loggedInUserSerialNumber = sharedPref.getString("loggedInUserSerialNumber", null)
        if (loggedInUserSerialNumber != null) {
            viewModel.loadAndUpdateCustomerData(loggedInUserSerialNumber)
            viewModel.getCustomerData(loggedInUserSerialNumber)
        } else {
            // Handle case where serial number is not available
            Timber.tag("DataActivity").e("Inverter serial number not found in SharedPreferences")
        }
    }
}



//
//class DataActivity : AppCompatActivity() {
//
//    private lateinit var viewModel: CustomerViewModel
//    private lateinit var recyclerView: RecyclerView
//    private lateinit var adapter: InverterDataAdapter
//    private lateinit var customToolbar: CustomToolbar // Reference to your custom toolbar
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_data)
//
//        // Initialize custom toolbar
//        customToolbar = findViewById(R.id.customToolbar) // Ensure this ID matches your layout
//        setSupportActionBar(customToolbar)
//
//        // Set the serial number in the toolbar
//        val currentSerialNumber = SerialNumberManager.getCurrentSerialNumber(this)
//        customToolbar.updateSerialNumber(currentSerialNumber ?: "testing0013") // Default value
//
//        // Initialize Room database and repository
//        val database = Room.databaseBuilder(
//            applicationContext,
//            CustomerDatabase::class.java,
//            "customer_db"
//        )
//            .fallbackToDestructiveMigration() // Wipes database if schema changes
//            .build()
//
//        // Initialize repository
//        val repository = CustomerRepository(database.customerDATAdAO(), RetrofitInstance.api)
//
//        // Initialize ViewModel using the repository
//        val factory = CustomerViewModelFactory(repository)
//        viewModel = ViewModelProvider(this, factory).get(CustomerViewModel::class.java)
//
//        // Set up RecyclerView
//        recyclerView = findViewById(R.id.recycler_view)
//        recyclerView.layoutManager = LinearLayoutManager(this)
//        recyclerView.addItemDecoration(SpaceItemDecoration(4)) // Adds spacing between items
//
//        // Observe customer data and update RecyclerView
//        viewModel.customerData.observe(this, Observer { data ->
//            data?.let {
//                Timber.d("Data from Room and server: $it")
//
//                // Check if adapter is already initialized
//                if (::adapter.isInitialized) {
//                    adapter.updateData(it)  // Refresh the RecyclerView with new data
//                } else {
//                    adapter = InverterDataAdapter(it.toMutableList()) // Initialize adapter
//                    recyclerView.adapter = adapter
//                }
//            }
//        })
//
//        // Observe error messages
//        viewModel.error.observe(this, Observer { errorMessage ->
//            errorMessage?.let {
//                Timber.tag("CustomerDataError").e(it)
//                // Show error in the UI if needed (e.g., Toast, Snackbar)
//            }
//        })
//
//        // Fetch customer data based on a specific serial number
//        val sharedPref = getSharedPreferences("UserPrefs", MODE_PRIVATE)
//        val loggedInUserSerialNumber = sharedPref.getString("loggedInUserSerialNumber", null)
//        if (loggedInUserSerialNumber != null) {
//            viewModel.loadAndUpdateCustomerData(loggedInUserSerialNumber)
//            viewModel.getCustomerData(loggedInUserSerialNumber)
//        } else {
//            // Handle case where serial number is not available
//            Timber.tag("DataActivity").e("Inverter serial number not found in SharedPreferences")
//        }
//
//        // Update the toolbar when the serial number changes
//        updateToolbarSerialNumber()
//    }
//
//    private fun updateToolbarSerialNumber() {
//        val currentSerialNumber = SerialNumberManager.getCurrentSerialNumber(this)
//        customToolbar.updateSerialNumber(currentSerialNumber ?: "testing0013") // Default value
//    }
//}

//
//class DataActivity : AppCompatActivity() {
//
//    private lateinit var viewModel: CustomerViewModel
//    private lateinit var recyclerView: RecyclerView
//    private lateinit var adapter: InverterDataAdapter
//    private lateinit var customToolbar: CustomToolbar // Reference to your custom toolbar
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_data)
//
//        // Initialize custom toolbar
//        customToolbar = findViewById(R.id.customToolbar) // Ensure this ID matches your layout
//        setSupportActionBar(customToolbar)
//
//        // Update the serial number in the toolbar
//        updateToolbarSerialNumber() // Set the initial serial number
//
//        // Initialize Room database and repository
//        val database = Room.databaseBuilder(
//            applicationContext,
//            CustomerDatabase::class.java,
//            "customer_db"
//        )
//            .fallbackToDestructiveMigration() // Wipes database if schema changes
//            .build()
//
//        // Initialize repository
//        val repository = CustomerRepository(database.customerDATAdAO(), RetrofitInstance.api)
//
//        // Initialize ViewModel using the repository
//        val factory = CustomerViewModelFactory(repository)
//        viewModel = ViewModelProvider(this, factory).get(CustomerViewModel::class.java)
//
//        // Set up RecyclerView
//        recyclerView = findViewById(R.id.recycler_view)
//        recyclerView.layoutManager = LinearLayoutManager(this)
//        recyclerView.addItemDecoration(SpaceItemDecoration(4)) // Adds spacing between items
//
//        // Observe customer data and update RecyclerView
//        viewModel.customerData.observe(this) { data ->
//            data?.let {
//                Timber.d("Data from Room and server: $it")
//
//                // Check if adapter is already initialized
//                if (::adapter.isInitialized) {
//                    adapter.updateData(it)  // Refresh the RecyclerView with new data
//                } else {
//                    adapter = InverterDataAdapter(it.toMutableList()) // Initialize adapter
//                    recyclerView.adapter = adapter
//                }
//            }
//        }
//
//        // Observe error messages
//        viewModel.error.observe(this) { errorMessage ->
//            errorMessage?.let {
//                Timber.tag("CustomerDataError").e(it)
//                // Show error in the UI if needed (e.g., Toast, Snackbar)
//            }
//        }
//
//        // Fetch customer data based on a specific serial number
//        val sharedPref = getSharedPreferences("UserPrefs", MODE_PRIVATE)
//        val loggedInUserSerialNumber = sharedPref.getString("loggedInUserSerialNumber", null)
//        if (loggedInUserSerialNumber != null) {
//            viewModel.loadAndUpdateCustomerData(loggedInUserSerialNumber)
//            viewModel.getCustomerData(loggedInUserSerialNumber)
//        } else {
//            // Handle case where serial number is not available
//            Timber.tag("DataActivity").e("Inverter serial number not found in SharedPreferences")
//        }
//
//        // Update the toolbar whenever the serial number changes
//        observeSerialNumberUpdates()
//    }
//
//    private fun updateToolbarSerialNumber() {
//        val currentSerialNumber = SerialNumberManager.getCurrentSerialNumber(this)
//        customToolbar.updateSerialNumber(currentSerialNumber ?: "testing0013") // Default value
//    }
//
//    private fun observeSerialNumberUpdates() {
//        // You could create a LiveData or similar mechanism in your ViewModel to observe serial number changes
//        viewModel.currentSerialNumber.observe(this) { newSerialNumber ->
//            customToolbar.updateSerialNumber(newSerialNumber ?: "testing0013") // Update toolbar with the new serial number
//        }
//    }
//}
//
//class DataActivity : AppCompatActivity() {
//
//    private lateinit var viewModel: CustomerViewModel
//    private lateinit var userInfoViewModel: UserInfoLoginViewModel // Reference to UserInfoLoginViewModel
//    private lateinit var recyclerView: RecyclerView
//    private lateinit var adapter: InverterDataAdapter
//    private lateinit var customToolbar: CustomToolbar // Reference to your custom toolbar
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_data)
//
//        // Initialize custom toolbar
//        customToolbar = findViewById(R.id.customToolbar) // Ensure this ID matches your layout
//        setSupportActionBar(customToolbar)
//
//        // Initialize Room database and repository
//        val database = Room.databaseBuilder(
//            applicationContext,
//            CustomerDatabase::class.java,
//            "customer_db"
//        )
//            .fallbackToDestructiveMigration() // Wipes database if schema changes
//            .build()
//
//        // Initialize repository
//        val repository = CustomerRepository(database.customerDATAdAO(), RetrofitInstance.api)
//
//        // Initialize ViewModel for Customer
//        val factory = CustomerViewModelFactory(repository)
//        viewModel = ViewModelProvider(this, factory).get(CustomerViewModel::class.java)
//
//        // Initialize ViewModel for UserInfo
//        userInfoViewModel = ViewModelProvider(
//            this,
//            UserInfoLoginViewModelFactory(UserInfoLoginRepository(RetrofitInstance.api))
//        ).get(UserInfoLoginViewModel::class.java)
//
//        // Set up RecyclerView
//        recyclerView = findViewById(R.id.recycler_view)
//        recyclerView.layoutManager = LinearLayoutManager(this)
//        recyclerView.addItemDecoration(SpaceItemDecoration(4)) // Adds spacing between items
//
//        // Observe customer data and update RecyclerView
//        viewModel.customerData.observe(this, Observer { data ->
//            data?.let {
//                Timber.d("Data from Room and server: $it")
//
//                // Check if adapter is already initialized
//                if (::adapter.isInitialized) {
//                    adapter.updateData(it)  // Refresh the RecyclerView with new data
//                } else {
//                    adapter = InverterDataAdapter(it.toMutableList()) // Initialize adapter
//                    recyclerView.adapter = adapter
//                }
//            }
//        })
//
//        // Observe error messages
//        viewModel.error.observe(this, Observer { errorMessage ->
//            errorMessage?.let {
//                Timber.tag("CustomerDataError").e(it)
//                // Show error in the UI if needed (e.g., Toast, Snackbar)
//            }
//        })
//
//        // Fetch customer data based on a specific serial number
//        val sharedPref = getSharedPreferences("UserPrefs", MODE_PRIVATE)
//        val loggedInUserSerialNumber = sharedPref.getString("loggedInUserSerialNumber", null)
//        if (loggedInUserSerialNumber != null) {
//            viewModel.loadAndUpdateCustomerData(loggedInUserSerialNumber)
//            viewModel.getCustomerData(loggedInUserSerialNumber)
//        } else {
//            // Handle case where serial number is not available
//            Timber.tag("DataActivity").e("Inverter serial number not found in SharedPreferences")
//        }
//
//        // Observe UserInfoViewModel for serial number updates
//        userInfoViewModel.userInfo.observe(this) { userInfoList ->
//            userInfoList.forEach { userInfo ->
//                if (userInfo.userid == getLoggedInUserId()) {
//                    val newSerialNumber = userInfo.inverterSerialNo
//                    customToolbar.updateSerialNumber(newSerialNumber ?: "Unknown") // Update toolbar
//                    saveInverterSerialNumber(newSerialNumber) // Save the serial number
//                }
//            }
//        }
//    }
//
//    // Retrieve the logged-in user ID from SharedPreferences
//    private fun getLoggedInUserId(): String? {
//        val sharedPref = getSharedPreferences("app_prefs", MODE_PRIVATE)
//        return sharedPref.getString("loggedInUserId", null)
//    }
//
//    // Save the inverter serial number to SharedPreferences
//    private fun saveInverterSerialNumber(serialNumber: String?) {
//        val sharedPref = getSharedPreferences("USER_SERIALNUMBER", MODE_PRIVATE)
//        with(sharedPref.edit()) {
//            putString("loggedInUserSerialNumber", serialNumber)
//            apply()
//        }
//    }
//}




// RecyclerView item decoration for adding space between items
class SpaceItemDecoration(private val space: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.bottom = space // Add space below each item
    }
}
