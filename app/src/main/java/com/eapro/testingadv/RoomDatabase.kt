package com.eapro.testingadv

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [CustomerDataResponse::class], version = 1)

abstract class CustomerDatabase:RoomDatabase(){


    abstract fun customerDATAdAO():CustomerDataDao

}



