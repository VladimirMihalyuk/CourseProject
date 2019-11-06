package com.example.courseproject.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DatabaseDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg cost: Costs)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDebt(cost: Depts)

    //Мои долги
    @Query("SELECT * FROM Depts WHERE IsMineDept == 1 and UserId = :userId")
    fun getAllMineDebts(userId: String):LiveData<List<Depts>>

    //Должен я
    @Query("SELECT * FROM Depts WHERE IsMineDept == 0 and UserId = :userId")
    fun getAllNotMineDebts(userId: String):LiveData<List<Depts>>



}