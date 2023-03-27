package com.rickyhu.hdriver.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rickyhu.hdriver.data.dao.GodItemDao
import com.rickyhu.hdriver.data.model.GodItem

@Database(entities = [GodItem::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun godItemDao(): GodItemDao

    companion object {
        private var INSTANCE : AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}