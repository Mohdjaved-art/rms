package com.eapro.testingadv
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import timber.log.Timber




//class MainActivity : AppCompatActivity() {
//
//    private lateinit var loginViewModel: LoginViewModel
//
//    private lateinit var authViewModel: AuthViewModel
//
//
//
//    @SuppressLint("MissingInflatedId")
//    override fun onCreate(savedInstanceState: Bundle?) {
//
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        // Create an instance of AuthRepository
//        val authRepository = AuthRepository(RetrofitInstance.api)
//
//
//
//
//        // Check if the user is already logged in
//        val sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
//        val isLoggedIn = sharedPreferences.getBoolean("is_logged_in", false)
//
//        if (isLoggedIn) {
//            // If user is already logged in, navigate to DashboardActivity
//            val intent = Intent(this, DashboardActivity::class.java)
//            startActivity(intent)
//            finish()
//            return
//        }
//
//        // Initialize ViewModel with the factory
//        val factory = LoginViewModelFactory(this)
//        loginViewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)
//
//        val authFactory = AuthViewModelFactory(authRepository)
//        authViewModel = ViewModelProvider(this, authFactory).get(AuthViewModel::class.java)
//
//        // Now you can use authViewModel to handle email/password operations
//      //  authViewModel.sendEmail("example@example.com")
//
//        val usernameEditText = findViewById<EditText>(R.id.user_name)
//        val passwordEditText = findViewById<EditText>(R.id.user_password)
//        val loginButton = findViewById<TextView>(R.id.login_btn)
//        val forgotPasswordText = findViewById<TextView>(R.id.forgotpass) // Make sure this TextView is in your layout
//
//        loginButton.setOnClickListener {
//            val username = usernameEditText.text.toString().trim()
//            val password = passwordEditText.text.toString().trim()
//
//            if (username.isEmpty() || password.isEmpty()) {
//                Toast.makeText(this, "Please enter both username and password.", Toast.LENGTH_SHORT).show()
//            } else {
//                loginViewModel.login(username, password) // Call login function
//            }
//        }
//
//        // Show forgot password dialog when the user clicks on the text
//        forgotPasswordText.setOnClickListener {
//            showForgotPasswordDialog()
//        }
//
//        // Observe the login result
////        loginViewModel.loginResult.observe(this) { response ->
////            if (response != null && response.RESPONSE == "success") {
////                Timber.tag("LoginResponse").d("Response: $response")
////
////                val userData = response.DATA.firstOrNull()
////
////                if (userData != null) {
////                    Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()
////
////                    // Save login status and userId
////                    with(sharedPreferences.edit()) {
////                        putBoolean("is_logged_in", true)
////                        putString("loggedInUserId", userData.userid)
////                        apply()
////                    }
////
////                    // Navigate to DashboardActivity when login is successful
////                    val intent = Intent(this, DashboardActivity::class.java)
////                    startActivity(intent)
////                    finish()
////                } else {
////                    Timber.tag("LoginError").e("User data is missing in the response")
////                    Toast.makeText(this, "User data not found. Please try again.", Toast.LENGTH_SHORT).show()
////                }
////            } else {
////                Toast.makeText(this, "Login failed. Please try again.", Toast.LENGTH_SHORT).show()
////            }
////        }
//
//
//
//        loginViewModel.loginResult.observe(this) { response ->
//            if (response != null && response.RESPONSE == "success") {
//                Timber.d("Response: $response")
//
//                val userData = response.DATA.firstOrNull()
//
//                if (userData != null) {
//                    Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()
//
//                    // Save login status and user details
//                    with(sharedPreferences.edit()) {
//                        putBoolean("is_logged_in", true)
//                        putString("loggedInUserId", userData.userid)
//                        putString("loggedInUserEmail", userData.emailaddress) // Store the email
//                        apply()
//                    }
//
//                    // Navigate to DashboardActivity when login is successful
//                    val intent = Intent(this, DashboardActivity::class.java)
//                    startActivity(intent)
//                    finish()
//                }
//            }
//        }
//
//
//        // Observe error messages
//        loginViewModel.errorMessage.observe(this) { message ->
//            message?.let {
//                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
//            }
//        }
//
//        // Observe email response for forgot password
//        authViewModel.emailResponse.observe(this) { response ->
//            Toast.makeText(this, "Email sent successfully!", Toast.LENGTH_SHORT).show()
//        }
//
//        authViewModel.error.observe(this) { errorMessage ->
//            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
//        }
//    }
//
//
////    private fun showForgotPasswordDialog() {
////        val dialog = Dialog(this)
////        dialog.setContentView(R.layout.dialog_forgot_password)
////        dialog.setCancelable(true)
////
////        val emailInput = dialog.findViewById<EditText>(R.id.email_input)
////        val submitButton = dialog.findViewById<TextView>(R.id.submit)
////
////        submitButton.setOnClickListener {
////            val email = emailInput.text.toString().trim()
////            if (email.isEmpty()) {
////                Toast.makeText(this, "Please enter your email.", Toast.LENGTH_SHORT).show()
////            } else {
////                // Call the sendEmail function from AuthViewModel
////                authViewModel.sendEmail(email)
////
////                // Dismiss the dialog
////                dialog.dismiss()
////
////                // Optionally show verification dialog or other actions
////                // showVerificationDialog(email)
////            }
////        }
////
////        dialog.show()
////    }
//
//
//    private fun showForgotPasswordDialog() {
//        val dialog = Dialog(this)
//        dialog.setContentView(R.layout.dialog_forgot_password)
//        dialog.setCancelable(true)
//
//        val emailInput = dialog.findViewById<EditText>(R.id.email_input)
//        val submitButton = dialog.findViewById<TextView>(R.id.submit)
//
//        submitButton.setOnClickListener {
//            val enteredEmail = emailInput.text.toString().trim()
//
//            if (enteredEmail.isEmpty()) {
//                Toast.makeText(this, "Please enter your email.", Toast.LENGTH_SHORT).show()
//            } else {
//                // Get the logged-in user's email
//                val savedEmail = sharedPreferences.getString("loggedInUserEmail", "")
//
//                // Check if entered email matches the logged-in email
//                if (enteredEmail == savedEmail) {
//                    authViewModel.sendEmail(enteredEmail) // Proceed with sending email
//                    dialog.dismiss() // Close the dialog after submission
//                } else {
//                    Toast.makeText(this, "Email does not match the logged-in user.", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
//
//        dialog.show()
//    }
//
//
//}




//package com.eapro.testingadv
//
//import android.annotation.SuppressLint
//import android.app.Dialog
//import android.content.Context
//import android.content.Intent
//import android.os.Bundle
//import android.widget.EditText
//import android.widget.TextView
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import androidx.lifecycle.ViewModelProvider
//import timber.log.Timber

//class MainActivity : AppCompatActivity() {
//
//    private lateinit var loginViewModel: LoginViewModel
//    private lateinit var authViewModel: AuthViewModel
//    private lateinit var sharedPreferences: android.content.SharedPreferences // Declare sharedPreferences here
//
//    @SuppressLint("MissingInflatedId")
//    override fun onCreate(savedInstanceState: Bundle?) {
//
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        // Initialize SharedPreferences at the class level
//        sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
//
//        // Create an instance of AuthRepository
//        val authRepository = AuthRepository(RetrofitInstance.api)
//
//        // Check if the user is already logged in
//        val isLoggedIn = sharedPreferences.getBoolean("is_logged_in", false)
//
//        if (isLoggedIn) {
//            // If user is already logged in, navigate to DashboardActivity
//            val intent = Intent(this, DashboardActivity::class.java)
//            startActivity(intent)
//            finish()
//            return
//        }
//
//        // Initialize ViewModels with factories
//        val factory = LoginViewModelFactory(this)
//        loginViewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)
//
//        val authFactory = AuthViewModelFactory(authRepository)
//        authViewModel = ViewModelProvider(this, authFactory).get(AuthViewModel::class.java)
//
//        val usernameEditText = findViewById<EditText>(R.id.user_name)
//        val passwordEditText = findViewById<EditText>(R.id.user_password)
//        val loginButton = findViewById<TextView>(R.id.login_btn)
//        val forgotPasswordText = findViewById<TextView>(R.id.forgotpass)
//
//        loginButton.setOnClickListener {
//            val username = usernameEditText.text.toString().trim()
//            val password = passwordEditText.text.toString().trim()
//
//            if (username.isEmpty() || password.isEmpty()) {
//                Toast.makeText(this, "Please enter both username and password.", Toast.LENGTH_SHORT).show()
//            } else {
//                loginViewModel.login(username, password)
//            }
//        }
//
//        forgotPasswordText.setOnClickListener {
//            showForgotPasswordDialog()
//        }
//
//        loginViewModel.loginResult.observe(this) { response ->
//            if (response != null && response.RESPONSE == "success") {
//                Timber.d("Response: $response")
//                val userData = response.DATA.firstOrNull()
//
//                if (userData != null) {
//                    Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()
//
//                    // Save login status and user details
//                    with(sharedPreferences.edit()) {
//                        putBoolean("is_logged_in", true)
//                        putString("loggedInUserId", userData.userid)
//                        putString("loggedInUserEmail", userData.emailaddress)
//                        apply()
//                    }
//
//                    val intent = Intent(this, DashboardActivity::class.java)
//                    startActivity(intent)
//                    finish()
//                }
//            }
//        }
//
//        loginViewModel.errorMessage.observe(this) { message ->
//            message?.let {
//                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
//            }
//        }
//
//        authViewModel.emailResponse.observe(this) { response ->
//            Toast.makeText(this, "Email sent successfully!", Toast.LENGTH_SHORT).show()
//        }
//
//        authViewModel.error.observe(this) { errorMessage ->
//            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
//        }
//    }
//
//
//
//    private fun showForgotPasswordDialog() {
//        val dialog = Dialog(this)
//        dialog.setContentView(R.layout.dialog_forgot_password)
//        dialog.setCancelable(true)
//
//        val emailInput = dialog.findViewById<EditText>(R.id.email_input)
//        val submitButton = dialog.findViewById<TextView>(R.id.submit)
//
//        submitButton.setOnClickListener {
//            val enteredEmail = emailInput.text.toString().trim()
//
//            if (enteredEmail.isEmpty()) {
//                Toast.makeText(this, "Please enter your email.", Toast.LENGTH_SHORT).show()
//            } else {
//                // Get the logged-in user's email
//                val savedEmail = sharedPreferences.getString("loggedInUserEmail", "")?.trim()
//
//                // Log both entered and saved emails for debugging
//                Timber.d("Entered email: $enteredEmail, Saved email: $savedEmail")
//
//                // If the entered email matches the saved email, send an email to both
//                if (enteredEmail.equals(savedEmail, ignoreCase = true)) {
//                    // Send email to the entered email
//                    authViewModel.sendEmail(enteredEmail)
//                    dialog.dismiss() // Close the dialog after submission
//                } else {
//                    // Send email to the entered email only
//                    authViewModel.sendEmail(enteredEmail)
//                    dialog.dismiss() // Close the dialog after submission
//                }
//            }
//        }
//
//        dialog.show()
//    }
//
//
//
//}




class MainActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var authViewModel: AuthViewModel
    private lateinit var sharedPreferences: android.content.SharedPreferences // Declare sharedPreferences here

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize SharedPreferences at the class level
        sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

        // Create an instance of AuthRepository
        val authRepository = AuthRepository(RetrofitInstance.api)

        // Check if the user is already logged in
        val isLoggedIn = sharedPreferences.getBoolean("is_logged_in", false)

        if (isLoggedIn) {
            // If user is already logged in, navigate to DashboardActivity
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

        // Initialize ViewModels with factories
        val factory = LoginViewModelFactory(this)
        loginViewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)

        val authFactory = AuthViewModelFactory(authRepository)
        authViewModel = ViewModelProvider(this, authFactory).get(AuthViewModel::class.java)

        val usernameEditText = findViewById<EditText>(R.id.user_name)
        val passwordEditText = findViewById<EditText>(R.id.user_password)
        val loginButton = findViewById<TextView>(R.id.login_btn)
        val forgotPasswordText = findViewById<TextView>(R.id.forgotpass)

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter both username and password.", Toast.LENGTH_SHORT).show()
            } else {
                loginViewModel.login(username, password)
            }
        }

        forgotPasswordText.setOnClickListener {
            showForgotPasswordDialog()
        }

        // Observer for login result
        loginViewModel.loginResult.observe(this) { response ->
            if (response != null && response.RESPONSE == "success") {
                Timber.d("Response: $response")
                val userData = response.DATA.firstOrNull()

                if (userData != null) {
                    Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()

                    // Save login status and user details
                    with(sharedPreferences.edit()) {
                        putBoolean("is_logged_in", true)
                        putString("loggedInUserId", userData.userid)
                        putString("loggedInUserEmail", userData.emailaddress)
                        apply()
                    }

                    val intent = Intent(this, DashboardActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }

        // Observer for error messages
        loginViewModel.errorMessage.observe(this) { message ->
            message?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }

        authViewModel.emailResponse.observe(this) { response ->
            if (response != null) {
                Toast.makeText(this, "Email response: $response", Toast.LENGTH_SHORT).show()

                // After sending the email, trigger OTP verification
                val enteredEmail = sharedPreferences.getString("loggedInUserEmail", "")?.trim()
                if (!enteredEmail.isNullOrEmpty()) {
                    authViewModel.sendOtp(enteredEmail)  // Trigger OTP
                }
            }
        }

        authViewModel.otpResponse.observe(this) { otpResponse ->
            if (otpResponse != null) {
                Toast.makeText(this, "OTP response: $otpResponse", Toast.LENGTH_SHORT).show()
            }
        }

        authViewModel.error.observe(this) { errorMessage ->
            Toast.makeText(this, "Error: $errorMessage", Toast.LENGTH_SHORT).show()
        }
    }


    private fun showForgotPasswordDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_forgot_password)
        dialog.setCancelable(true)

        val emailInput = dialog.findViewById<EditText>(R.id.email_input)
        val submitButton = dialog.findViewById<TextView>(R.id.submit)

        submitButton.setOnClickListener {
            val enteredEmail = emailInput.text.toString().trim()

            if (enteredEmail.isEmpty()) {
                Toast.makeText(this, "Please enter your email.", Toast.LENGTH_SHORT).show()
            } else {
                // Get the logged-in user's email
                val savedEmail = sharedPreferences.getString("loggedInUserEmail", "")?.trim()

                // Log both entered and saved emails for debugging
                Timber.d("Entered email: $enteredEmail, Saved email: $savedEmail")

                // If the entered email matches the saved email, send an email to both
                if (enteredEmail.equals(savedEmail, ignoreCase = true)) {
                    // Send email to the entered email and logged-in user email
                    authViewModel.sendEmail(enteredEmail)
                } else {
                    // Send email to the entered email only
                    authViewModel.sendEmail(enteredEmail)
                }

                // Dismiss the dialog after submitting
                dialog.dismiss()
            }
        }

        dialog.show()

        // Observe emailResponse to show Toast when email is sent successfully
        authViewModel.emailResponse.observe(this) { response ->
            if (!response.isNullOrEmpty()) {
                // Show Toast with email response
                Toast.makeText(this, "Email sent successfully! Response: $response", Toast.LENGTH_SHORT).show()
            }
        }

        // Observe error messages to show error Toasts if something goes wrong
        authViewModel.error.observe(this) { errorMessage ->
            if (!errorMessage.isNullOrEmpty()) {
                Toast.makeText(this, "Error: $errorMessage", Toast.LENGTH_SHORT).show()
            }
        }
    }

}























