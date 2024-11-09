package com.eapro.testingadv

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel



class UserInfoLoginViewModel(private val repository: UserInfoLoginRepository) : ViewModel() {

    private val _userInfo = MutableLiveData<List<UserInfoResponse>>()
    val userInfo: LiveData<List<UserInfoResponse>> = _userInfo

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    fun getUserInfo() {
        repository.getUserInfo { result ->
            result.onSuccess { data ->
                _userInfo.postValue(data)
            }.onFailure { error ->
                _errorMessage.postValue(error.message)
            }
        }
    }
}
