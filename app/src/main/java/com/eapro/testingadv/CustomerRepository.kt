package com.eapro.testingadv
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber




class CustomerRepository(
    private val customerDataDao: CustomerDataDao,
    private val apiService: ApiService  // Assuming your Retrofit instance
) {

    // Insert data into Room database
    suspend fun insertDataIntoRoom(data: List<CustomerDataResponse>) {
        Timber.d("Inserting data into Room: $data")
        customerDataDao.insertData(data)
    }

    // Retrieve data from Room database
    suspend fun getAllDataFromRoom(): List<CustomerDataResponse> {
        val data = customerDataDao.getAllData()
        Timber.d("Retrieved data from Room: $data")
        return data
    }

    // Fetch data from the server
    fun fetchDataFromServer(
        deviceslno: String,
        onResult: (Result<List<CustomerDataResponse>>) -> Unit
    ) {
        val request = CustomerDataRequest(deviceslno)

        val call = apiService.fetchCustomerData(request)

        call.enqueue(object : Callback<List<CustomerDataResponse>> {
            override fun onResponse(
                call: Call<List<CustomerDataResponse>>,
                response: Response<List<CustomerDataResponse>>
            ) {
                if (response.isSuccessful) {
                    val serverData = response.body()
                    if (serverData != null) {
                        // Save the server data to Room
                        CoroutineScope(Dispatchers.IO).launch {
                            insertDataIntoRoom(serverData)
                        }
                    }
                    // Pass the server data to the callback
                    onResult(Result.success(serverData ?: listOf()))
                } else {
                    onResult(Result.failure(Throwable("Error fetching data from server")))
                }
            }

            override fun onFailure(call: Call<List<CustomerDataResponse>>, t: Throwable) {
                onResult(Result.failure(t))
            }
        })
    }

    // Combine Room and Server data retrieval
    suspend fun getCustomerData(deviceslno: String): List<CustomerDataResponse> {
        val roomData = getAllDataFromRoom()

        // Fetch data from the server and update Room
        fetchDataFromServer(deviceslno) { result ->
            result.onSuccess { serverData ->
                if (serverData.isNotEmpty()) {
                    Timber.d("Fetched data from server: $serverData")
                }
            }.onFailure {
                Timber.e("Error fetching data from server: ${it.message}")
            }
        }

        return roomData
    }
}

