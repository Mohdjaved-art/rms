package com.eapro.testingadv



import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

// for login user viewmodel

class LoginViewModel(@SuppressLint("StaticFieldLeak") private val context: Context) : ViewModel() {

    private val repository = LoginRepository() // Repository object

    private val _loginResult = MutableLiveData<LoginResponse?>()  // Read-only LiveData
    val loginResult: LiveData<LoginResponse?> = _loginResult

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    // Save login status to SharedPreferences
    fun saveLoginStatus(isLoggedIn: Boolean) {
        val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("is_logged_in", isLoggedIn)
        editor.apply()
    }

    // Check if user is logged in
    fun isUserLoggedIn(): Boolean {
        val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("is_logged_in", false)
    }

    // Perform login
    fun login(username: String, password: String) {
        repository.loginUser(username, password) { response ->
            if (response != null) {
                _loginResult.postValue(response)
                saveLoginStatus(true) // Save login status if login is successful
                _errorMessage.postValue(response.MESSAGE)
            } else {
                _loginResult.postValue(null)
                _errorMessage.postValue("An error occurred during login.")
            }
        }
    }
}

