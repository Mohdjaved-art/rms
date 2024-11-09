package com.eapro.testingadv

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.*
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber


class CustomerDataRepository(private val apiService: ApiService) {

    private val TAG = "CustomerDataRepository"

    suspend fun getCustomerData(deviceslno: String, date: String, date2: String): List<InverterData> {
        Log.d(TAG, "getCustomerData called with deviceslno: $deviceslno, date: $date, date2: $date2")
        return try {
            val data = apiService.downloadCustomerData(CustomerDownloadData(deviceslno, date, date2))
            Log.d(TAG, "Data fetched successfully: $data")
            data
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching customer data: ${e.message}", e)
            throw e // Re-throw the exception to be handled in ViewModel
        }
    }
}


class CustomerDataViewModel(private val repository: CustomerDataRepository) : ViewModel() {

    private val TAG = "CustomerDataViewModel"

    private val _customerData = MutableLiveData<List<InverterData>>()
    val customerData: LiveData<List<InverterData>> get() = _customerData

    fun fetchCustomerData(request: CustomerDownloadData) {
        Log.d(TAG, "fetchCustomerData called with deviceslno: ${request.deviceslno}, date: ${request.date}, date2: ${request.date2}")

        viewModelScope.launch {
            try {
                val data = repository.getCustomerData(request.deviceslno, request.date, request.date2)
                _customerData.postValue(data)
                Log.d(TAG, "Customer data updated successfully with size: ${data.size}")
            } catch (e: Exception) {
                // Handle error (optional)
                Log.e(TAG, "Error fetching customer data: ${e.message}", e)
                // Optionally, you can post an error message to another LiveData to inform the UI
            }
        }
    }
}




class CustomerDataViewModelFactory(private val repository: CustomerDataRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CustomerDataViewModel::class.java)) {
            return CustomerDataViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class CustomerDataAdapter(private val customerDataList: List<InverterData>) :
    RecyclerView.Adapter<CustomerDataAdapter.CustomerDataViewHolder>() {

    private val TAG = "CustomerDataAdapter"

    class CustomerDataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDate: TextView = itemView.findViewById(R.id.tvDate)
        val tvTime: TextView = itemView.findViewById(R.id.tvTime)
        val tvSerialNo: TextView = itemView.findViewById(R.id.tvSerialNo)
        val tvIpVolt: TextView = itemView.findViewById(R.id.tvInputVolt)
        // Add other TextViews as needed
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerDataViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_customer_data, parent, false)
        Log.d("otherdata", "onCreateViewHolder called")
        return CustomerDataViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CustomerDataViewHolder, position: Int) {
        val customerData = customerDataList[position]
        Log.d(TAG, "onBindViewHolder called for position $position with data: $customerData")

        // Set data to TextViews
        holder.tvDate.text = customerData.date
        holder.tvTime.text = customerData.time
        holder.tvSerialNo.text = customerData.serialNo
        holder.tvIpVolt.text = customerData.ipVolt
        Log.d("adapterdata", "Bound data for position $position: Date: ${customerData.date}, Time: ${customerData.time}, SerialNo: ${customerData.serialNo}, IpVolt: ${customerData.ipVolt}")

        // Bind other fields similarly, and log them if needed
    }

    override fun getItemCount(): Int {
        val size = customerDataList.size
        Log.d("datasize", "getItemCount called, size: $size")
        return size
    }
}








