package com.example.courseproject.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(entities = [User::class, Depts::class, Income::class, Costs::class, IncomeType::class, CostType::class ],
    version = 3, exportSchema = true)
@TypeConverters(Converters::class)
abstract class AppDatabase  : RoomDatabase() {
    abstract val databaseDao: DatabaseDAO
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "Database.db"
                    ).fallbackToDestructiveMigration()
                     //.createFromAsset("database/Database.db")
                     .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}