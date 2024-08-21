package com.example.curd.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Subscriber::class], version = 1)
abstract class SubscriberDatabase : RoomDatabase(){

    abstract val subscriberDAO: SubscriberDAO

    companion object{
        @Volatile // to cater for multithreaded issue - ensure that changes to the instance are visible to every thread accessing it.
        private var INSTANCE: SubscriberDatabase? = null //singleton instance of the database
        fun getInstance(context: Context): SubscriberDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SubscriberDatabase::class.java,
                        "subscriber_data_database"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }

    }


}