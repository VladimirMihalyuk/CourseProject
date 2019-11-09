package com.example.courseproject.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DatabaseDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg cost: Costs)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDebt(debt: Debts)

    //Мои долги
    @Query("SELECT * FROM Debts WHERE IsMineDept == 1 and UserId = :userId and IsActive = 1")
    fun getAllMineDebts(userId: String):LiveData<List<Debts>>

    //Должен я
    @Query("SELECT * FROM Debts WHERE IsMineDept == 0 and UserId = :userId and IsActive = 1")
    fun getAllNotMineDebts(userId: String):LiveData<List<Debts>>

    @Update
    fun updateDebt(debt:Debts)

    @Query("SELECT * FROM CostType LEFT JOIN Costs ON IdOfCoost=IdOfCostType " +
            "and UserId = :userId ORDER BY DateOfCost DESC")
    fun getAllCosts(userId: String):LiveData<List<CostsRequestItem>>

    @Query("SELECT IncomeType.IdOfIncomeType, Type, Icon, Id, Description, UserId," +
            " AmountOfMoney, DateOfIncome FROM IncomeType LEFT JOIN Income ON " +
            "IncomeType.IdOfIncomeType = Income.IdOfIncomeType and " +
            "UserId = :userId ORDER BY DateOfIncome DESC")
    fun getAllIncome(userId: String):LiveData<List<IncomeRequestItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertIncome(income: Income)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCost(cost: Costs)

    @Query("SELECT Description, AmountOfMoney," +
            " DateOfCost, Icon FROM Costs INNER JOIN CostType ON IdOfCostType == IdOfCoost WHERE UserId = :userId")
    fun getAllCostsForHistory(userId: String):LiveData<List<HistoryCostRequest>>

    @Query("SELECT Description, AmountOfMoney, DateOfIncome, Icon FROM Income INNER JOIN " +
            "IncomeType ON Income.IdOfIncomeType == IncomeType.IdOfIncomeType WHERE UserId = :userId")
    fun getAllIncomeForHistory(userId: String):LiveData<List<HistoryIncomeRequest>>

    @Query("SELECT Type, SUM(AmountOfMoney) * 100.0 /(SELECT SUM(AmountOfMoney) FROM COSTS WHERE DateOfCost > :date AND UserId = :userId)  AS Value  FROM Costs INNER JOIN CostType ON IdOfCoost == IdOfCostType WHERE DateOfCost > :date AND UserId = :userId GROUP BY IdOfCostType; ")
    fun getCostForPieCharts(userId: String, date: Long):LiveData<List<PieChartResults>>

}