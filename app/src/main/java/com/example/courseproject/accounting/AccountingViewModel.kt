package com.example.courseproject.accounting

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.courseproject.App
import com.example.courseproject.database.AccountingItem
import com.example.courseproject.database.AccountingItemInfo
import com.example.courseproject.repository.Repository
import java.util.*
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.courseproject.database.Costs
import com.example.courseproject.database.Income
import java.lang.Exception
import java.text.SimpleDateFormat



class AccountingViewModel(val repository: Repository) : ViewModel() {
    private val costs =
        repository.getAllCosts(App.prefs?.idClient ?: "")


    init {
        App.prefs?.idClient?.let{
            repository.loadAllCosts(App.prefs?.idClient ?: "")
            repository.loadAllIncome(App.prefs?.idClient ?: "")
        }

    }

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


    val description = MutableLiveData<String>()
    val descriptionCorrect = MutableLiveData<Boolean>()
    fun checkDescription(){
        descriptionCorrect.value = description.value?.length ?: 0 >0
    }

    val money = MutableLiveData<String>()
    val moneyCorrect = MutableLiveData<Boolean>()
    var moneyValue:Float = 0F
    fun checkMoney(){
       try{
           moneyValue = money.value!!.toFloat()
           moneyCorrect.value = true
       } catch (e: Exception){
           moneyCorrect.value = false
       }
    }


    val totalIncome: LiveData<String> =  Transformations.map(incomeItemList){
        it.getTotalCount()
    }


    var dateString = MutableLiveData<String>("--/--/----")

    private lateinit var date:Date

    fun setDate(day: Int, monthOfYear: Int, year: Int){
        val cal = Calendar.getInstance()
        cal.set(Calendar.YEAR, year)
        cal.set(Calendar.MONTH, monthOfYear - 1)
        cal.set(Calendar.DAY_OF_MONTH, day)
        date = cal.time
        dateString.value="$day/$monthOfYear/$year"
        Log.d("WTF", "$date")
    }


    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private fun startLoading(){
        _isLoading.value = true
    }

    private fun stopLoading(){
        _isLoading.value = false
        _finishStatus.value = true
        dropFields()
    }

    private val _finishStatus = MutableLiveData<Boolean>()
    val finishStatus: LiveData<Boolean>
        get() = _finishStatus



    private fun checkDate() = !dateString.value.equals("--/--/----")

    private fun dropFields(){
        dateString.value = ("--/--/----")
        money.value = null
        description.value = null
    }
    fun addItem(){
        checkMoney()
        checkDescription()
        if(checkDate() && moneyCorrect.value == true && descriptionCorrect.value == true){
            val userId = App.prefs?.idClient ?: ""
            startLoading()
            if(itemInfo.AccountingType == 1){

                val income = Income("", description.value ?: "", userId, moneyValue,
                    date, itemInfo.IdOfICType)
                repository.insertIncome(userId, income){
                    stopLoading()
                }

            } else {
                val cost = Costs("", description.value ?: "", userId, moneyValue,
                    date, itemInfo.IdOfICType)
                repository.insertCost(userId, cost){
                    stopLoading()
                }
            }
        }else {
            Log.d("WTF", "FINISHSTATUS")
            _finishStatus.value = false
        }

    }

    companion object{
        private  val formatter = SimpleDateFormat("dd-MM-yyyy",
            App.getContext().resources.configuration.locale)
        fun parseDateToString(date: Date): String{
            return formatter.format(date)
        }
        lateinit var  itemInfo: AccountingItemInfo


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