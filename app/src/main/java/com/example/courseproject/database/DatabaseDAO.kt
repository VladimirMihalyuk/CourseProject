package com.example.courseproject.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DatabaseDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg cost: Costs)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDebt(cost: Debts)

    //Мои долги
    @Query("SELECT * FROM Debts WHERE IsMineDept == 1 and UserId = :userId and IsActive = 1")
    fun getAllMineDebts(userId: String):LiveData<List<Debts>>

    //Должен я
    @Query("SELECT * FROM Debts WHERE IsMineDept == 0 and UserId = :userId and IsActive = 1")
    fun getAllNotMineDebts(userId: String):LiveData<List<Debts>>

    @Update
    fun updateDebt(debt:Debts)


}