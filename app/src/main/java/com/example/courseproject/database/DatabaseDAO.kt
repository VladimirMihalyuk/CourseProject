package com.example.courseproject.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DatabaseDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg cost: Costs)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDebt(cost: Depts)

    //Мои долги
    @Query("SELECT * FROM Depts WHERE IsMineDept == 1 and UserId = :userId and IsActive = 1")
    fun getAllMineDebts(userId: String):LiveData<List<Depts>>

    //Должен я
    @Query("SELECT * FROM Depts WHERE IsMineDept == 0 and UserId = :userId and IsActive = 1")
    fun getAllNotMineDebts(userId: String):LiveData<List<Depts>>

    @Update
    fun updateDebt(debt:Depts)


}