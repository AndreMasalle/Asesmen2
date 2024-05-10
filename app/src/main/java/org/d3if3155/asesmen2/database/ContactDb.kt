package org.d3if3155.asesmen2.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.d3if3155.asesmen2.data.Contact

@Database(entities = [Contact::class], version = 1, exportSchema = false)
abstract class ContactDb : RoomDatabase(){
    abstract val dao: ContactDao

    companion object

    @Volatile
    private var INSTANCE : ContactDb? = null

    fun getInstance(context: Context): ContactDb{
        synchronized(this){
            var instance = INSTANCE

            if (instance== null){
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    ContactDb::class.java,
                    "catatan.db"
                ).build()
                INSTANCE = instance
            }
            return instance

        }
    }
}