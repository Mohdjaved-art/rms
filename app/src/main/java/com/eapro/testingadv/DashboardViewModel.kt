package com.eapro.testingadv

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import timber.log.Timber


class DashboardViewModel(private val repository: DashboardRepository) : ViewModel() {

    private val _dashboardData = MutableLiveData<List<InverterData>?>()
    val dashboardData: MutableLiveData<List<InverterData>?> get() = _dashboardData

    fun fetchDashboardData(payload: DashboardPayload) {
        viewModelScope.launch {
            val data = repository.getDashboardData(payload)

            if (data != null && data.isNotEmpty()) {
                Timber.tag("DashboardViewModel").d("Data fetched successfully: %s", data)
                _dashboardData.value = data
            } else if (data == null) {
                Timber.tag("DashboardViewModel").e("No data received: Data is null")
            } else {
                Timber.tag("DashboardViewModel").e("No data received: Data is empty")
            }
        }
    }

}

