package com.example.courseproject.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import kotlinx.android.parcel.Parcelize
import java.util.*

data class IncomeRequestItem(
    @ColumnInfo(name = "IdOfIncomeType")
    val IdOfIncomeType: Int,

    val Type: String,

    val Icon: String,

    val Id: String?,

    val Description: String?,

    val UserId: String?,

    val AmountOfMoney: Float?,

    val DateOfIncome: Date?
)

@Parcelize
data class AccountingItemInfo(
    val AccountingType: Int, //0 - costs 1-income

    val Type: String,

    val Icon: String,

    var IdOfICType: Int,

    val AmountOfMoney: Float
): Parcelable

data class AccountingItem(
    val AccountingType: Int, //0 - costs 1-income

    var Id: String? = "",

    var Description: String?,

    var UserId: String?,

    var AmountOfMoney: Float?,

    var Date: Date?,

    var IdOfICType: Int?
)

data class CostsRequestItem(
    val IdOfCoost: Int,

    val Type: String,

    val Icon: String,

    val Id: String?,

    val Description: String?,

    val UserId: String?,

    val AmountOfMoney: Float?,

    val DateOfCost: Date?,

    val IdOfCostType: Int?
)

data class HistoryItem(
    val TypeOfItem: Int, //1 - income 0 -cost

    val Description: String,

    val AmountOfMoney: Float,

    val DateOfItem:Date,

    val Icon: String
){
    constructor(item: HistoryIncomeRequest):
            this(1, item.Description,
                item.AmountOfMoney, item.DateOfIncome, item.Icon)
    constructor(item: HistoryCostRequest):
        this(0, item.Description,
            item.AmountOfMoney, item.DateOfCost, item.Icon)

}

data class HistoryCostRequest(
    val Description: String,

    val AmountOfMoney: Float,

    val DateOfCost: Date,

    val Icon: String
)

data class HistoryIncomeRequest(
    val Description: String,

    val AmountOfMoney: Float,

    val DateOfIncome: Date,

    val Icon: String
)

data class PieChartResults(
        val Type:String,

        val Value:Float
)