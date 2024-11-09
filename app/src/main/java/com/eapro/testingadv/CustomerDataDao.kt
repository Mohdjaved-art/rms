package com.eapro.testingadv

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
@Dao
interface CustomerDataDao { // always make this interface


    @Insert(onConflict = OnConflictStrategy.REPLACE)

    suspend fun insertData (data:List<CustomerDataResponse>)

    @Query("SELECT * FROM customer_data")

    suspend fun getAllData():List<CustomerDataResponse>

}

// first we have a Data Dao

// then we have a table for data

//