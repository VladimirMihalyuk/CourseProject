package com.example.courseproject.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import kotlinx.android.parcel.Parcelize
import java.util.*

@Entity
data class User(
    @PrimaryKey
    var Id: String = "",

    var email: String,

    var password: String
)

@Entity
data class Debts(
    @PrimaryKey
    var Id: String = "",

    var UserId: String,

    var Money: Float,

    var IsMineDept: Int,

    var Name: String,

    var IsActive: Int
){
    constructor():this("", "", 0F, 1, "", 1)
}

@Entity
data class Income(
    @PrimaryKey
    var Id: String = "",

    var Description: String,

    var UserId: String,

    var AmountOfMoney: Float,

    var DateOfIncome: Date,

    var IdOfIncomeType: Int
)

@Entity
data class Costs(
    @PrimaryKey
    var Id: String = "",

    var Description: String,

    var UserId: String,

    var AmountOfMoney: Float,

    var DateOfCost: Date,

    var IdOfCostType: Int
)



@Entity
data class IncomeType(
    @PrimaryKey(autoGenerate = true)
    var IdOfIncomeType: Int = 0,

    var Type: String,

    var Icon: String
)

@Entity
data class CostType(
    @PrimaryKey(autoGenerate = true)
    var IdOfCoost: Int = 0,

    var Type: String,

    var Icon: String
)


class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }
}

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

data class AccountingItem(
    val AccountingType: Int, //0 - costs 1-income

    var Id: String? = "",

    var Description: String?,

    var UserId: String?,

    var AmountOfMoney: Float?,

    var Date: Date?,

    var IdOfICType: Int?
)

@Parcelize
data class AccountingItemInfo(
    val AccountingType: Int, //0 - costs 1-income

    val Type: String,

    val Icon: String,

    var IdOfICType: Int,

    val AmountOfMoney: Float
): Parcelable

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