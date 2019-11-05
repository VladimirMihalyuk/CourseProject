package com.example.courseproject.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface DatabaseDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg cost: Costs)

    @Insert
    fun insertDept(cost: Depts)
}