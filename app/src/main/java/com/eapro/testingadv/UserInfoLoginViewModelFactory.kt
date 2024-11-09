package com.eapro.testingadv

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class UserInfoLoginViewModelFactory(private val repository: UserInfoLoginRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserInfoLoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserInfoLoginViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
