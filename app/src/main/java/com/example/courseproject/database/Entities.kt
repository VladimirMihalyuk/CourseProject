package com.example.courseproject.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.util.*

@Entity
data class User(
    @PrimaryKey
    var Id: String = "",

    var email: String,

    var password: String
)

@Entity
data class Depts(
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

    var UserId: String,

    var AmountOfMoney: Float,

    var DateOfIncome: Date,

    var IdOfIncomeType: Int
)

@Entity
data class Costs(
    @PrimaryKey
    var Id: String = "",

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

