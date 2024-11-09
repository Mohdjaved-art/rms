package com.eapro.testingadv
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*


interface ApiService {
    @POST("Login/Login")
//    @Headers("Content-Type: application/json")
    fun loginUser(@Body loginRequest: LoginRequest): Call<LoginResponse>  // login repository pass the request here for working with api response


    @Headers("Content-Type: application/json")
    @POST("CusData/Customer_Data")
    fun fetchCustomerData(@Body request: CustomerDataRequest): Call<List<CustomerDataResponse>> // for customer data

    @POST("Alert/Get_Alerts")
    suspend fun getAlerts(@Body requestData: AlertRequest): Response<List<AlertResponse>>


    @GET("UserInfo/Get_UserInfo")
    fun getUserInfo(): Call<List<UserInfoResponse>>

    @POST("Dashboard/Get_Dashboard_Data")
    suspend fun getDashboardData(@Body payload: DashboardPayload): Response<List<InverterData>>


    @POST("Login/SendMail_")
    suspend fun sendPasswordResetEmail(@Body emailRequest: EmailRequest): Response<String>  // Use String

    @POST("Login/Otpverification")
    suspend fun verifyOtp(@Body otpRequest: OtpRequest): Response<String>  // Use String

    @POST("Dashboard/ChangeSwitch")
        suspend fun changeSwitch(@Body deviceStatus: DeviceStatus): ApiResponse



    @POST("Dashboard/ChangeSwitchMode")
    suspend fun sendDeviceChangeStatus(@Body deviceStatus: deviceswitch): ApiResponse



    @POST("Dashboard/ChangeLowHigh")
    suspend fun sendDeviceLowStatus(@Body deviceStatus: Lowswitch): ApiResponse


        @POST("Dashboard/ModalGet_BarChart_Data")
        suspend fun getBarChartData(
            @Body payload: Map<String, String>
        ): List<BarChartData>





        @POST("Download/CUstome_Down_Reports")
        suspend fun downloadCustomerData(
            @Body request: CustomerDownloadData
        ): List<InverterData>



}
// for download reports data class here we have

data class CustomerDownloadData(
    val deviceslno: String,
    val date: String,
    val date2: String
)


data class Lowswitch(
    val charging: Boolean?=null,
    val deviceslno: String?=null
)

data class DeviceStatus(
    val onoff: Boolean? = null,  // Rename `charging` to `onoff`
    val deviceslno: String? = null  // Rename `deviceSlNo` to `deviceslno`
)


// for testing purpose
data class deviceswitch(
    val switchmode: Boolean?=false,  // or null  it is false here
    val deviceslno: String?=null

)





//data class for button here

data class ApiResponse(  // used in common in all the switches
    val USERID: String?,
    val RESPONSE: String,
    val MESSAGE: String
)





data class EmailRequest(
    val emailid: String // Assuming the API accepts an "emailid" field
)

data class OtpRequest(
    val emailid: String // Assuming the API accepts an "emailid" field
)



data class BarChartData(
    val label: String,
    val value: Double,
)



