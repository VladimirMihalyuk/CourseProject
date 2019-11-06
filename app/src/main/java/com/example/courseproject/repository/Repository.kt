package com.example.courseproject.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.courseproject.database.AppDatabase
import com.example.courseproject.database.DatabaseDAO
import com.example.courseproject.database.Depts
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

    fun getAllMineDebts(userId: String): LiveData<List<Depts>>{
        return database.getAllMineDebts(userId)
    }

    fun getAllNotMineDebts(userId: String):LiveData<List<Depts>>{
        return database.getAllNotMineDebts(userId)
    }

    fun loadAllDebts(userId: String){
        firebaseHelper.loadAllDebts(userId){
            loadDebtCallback(it)
        }
    }

    private fun loadDebtCallback(debts: MutableList<Depts>){
        Log.d("WTF", "$debts")
        ioScope.launch {
            for(debt in debts){
                database.insertDebt(debt)
            }

        }
    }
}