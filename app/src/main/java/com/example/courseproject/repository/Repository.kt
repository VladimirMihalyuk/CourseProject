package com.example.courseproject.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.courseproject.database.AppDatabase
import com.example.courseproject.database.CostsRequestItem
import com.example.courseproject.database.DatabaseDAO
import com.example.courseproject.database.Debts
import com.example.courseproject.firebase.FirebaseHelper
import kotlinx.coroutines.*

class Repository private constructor( private var  firebaseHelper:FirebaseHelper, private var  database: DatabaseDAO){
    private var viewModelJob = Job()
    private val ioScope = CoroutineScope(Dispatchers.IO + viewModelJob)

    companion object{
        @Volatile
        private var INSTANCE: Repository? = null
        fun getInstance(context: Context):Repository{
            synchronized(this) {
                var instance = INSTANCE
                if(instance == null){
                    instance = Repository(FirebaseHelper(), AppDatabase.getInstance(context).databaseDao)
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

    fun logIn(email: String, password: String, completionListener: (isSuccessful: Boolean) -> Unit){
        firebaseHelper.signIn(email, password, completionListener)
    }

    fun logUp(email: String, password: String, completionListener: (isSuccessful: Boolean) -> Unit){
        firebaseHelper.signUp(email, password, completionListener)
    }

    fun addDebt(userId: String, money: Float, isMineDept: Int, name: String, isActive: Int, finishCallback :() -> Unit){
        val debt = firebaseHelper.addDebt(userId, money, isMineDept, name, isActive)
        ioScope.launch {
            database.insertDebt(debt)
            withContext(Dispatchers.Main){
                finishCallback()
            }

        }
    }

    fun getAllMineDebts(userId: String): LiveData<List<Debts>>{
        return database.getAllMineDebts(userId)
    }

    fun getAllNotMineDebts(userId: String):LiveData<List<Debts>>{
        return database.getAllNotMineDebts(userId)
    }

    fun loadAllDebts(userId: String){
        firebaseHelper.loadAllDebts(userId){
            loadDebtCallback(it)
        }
    }

    private fun loadDebtCallback(debts: MutableList<Debts>){
        ioScope.launch {
            for(debt in debts){
                database.insertDebt(debt)
            }
        }
    }

    fun updateDebt(debt: Debts){
        debt.IsActive = 0
        firebaseHelper.updateDebt(debt)
        updateDebtDB(debt)
    }

    private fun updateDebtDB(debt: Debts){
        ioScope.launch {
            database.updateDebt(debt)
        }
    }

    fun getAllCosts(userId: String) = database.getAllCosts(userId)

    fun getAllIncome(userId: String) = database.getAllIncome(userId)


}