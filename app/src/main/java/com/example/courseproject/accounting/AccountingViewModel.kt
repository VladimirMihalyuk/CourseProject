package com.example.courseproject.accounting

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.courseproject.App
import com.example.courseproject.database.AccountingItem
import com.example.courseproject.database.AccountingItemInfo
import com.example.courseproject.repository.Repository

class AccountingViewModel(val repository: Repository) : ViewModel() {
    private val costs =
        repository.getAllCosts(App.prefs?.idClient ?: "")

    val costsItemsMap: LiveData<Map<Triple<String, String, Int>, List<AccountingItem>>> =
        Transformations.map(costs){ list ->
        list.map{Triple(it.Type , it.Icon, it.IdOfCoost) to
                AccountingItem(0, it.Id, it.Description, it.UserId, it.AmountOfMoney,
                    it.DateOfCost, it.IdOfCostType)}.groupBy({  it.first }, {it.second})
    }

    val costsItemList: LiveData<List<AccountingItemInfo>> = Transformations.map(costsItemsMap){ map ->
        map.map { AccountingItemInfo(0, it.key.first, it.key.second, it.key.third,
            it.value.count()) }

    }

    val totalCosts: LiveData<String> = Transformations.map(costsItemList){
        it.getTotalCount()
    }

    private val income
            = repository.getAllIncome(App.prefs?.idClient ?: "")

    val incomeItemsMap: LiveData<Map<Triple<String, String, Int>, List<AccountingItem>>>
            = Transformations.map(income){ list ->
        list.map{Triple(it.Type, it.Icon, it.IdOfIncomeType) to
                AccountingItem(1 , it.Id, it.Description, it.UserId,
                    it.AmountOfMoney, it.DateOfIncome, it.IdOfIncomeType)}
            .groupBy ({it.first}, {it.second})
    }

    val incomeItemList: LiveData<List<AccountingItemInfo>> = Transformations.map(incomeItemsMap){ map ->
        map.map { AccountingItemInfo(1, it.key.first, it.key.second, it.key.third,
            it.value.count()) }

    }

    val totalIncome: LiveData<String> =  Transformations.map(incomeItemList){
        it.getTotalCount()
    }

}


fun  List<AccountingItem>.count(): Float{
    var result = 0F
    for(item in this){
        result += item.AmountOfMoney ?: 0F
    }
    return result
}

fun List<AccountingItemInfo>.getTotalCount(): String{
    var count = 0F
    for(item in this){
        count += item.AmountOfMoney
    }
    return count.toString() + "BYN"
}