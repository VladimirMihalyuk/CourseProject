package com.example.courseproject.repository

import android.content.Context
import com.example.courseproject.database.AppDatabase
import com.example.courseproject.database.DatabaseDAO
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
            database.insertDept(debt)
            withContext(Dispatchers.Main){
                finishCallback()
            }

        }
    }
}