package com.example.courseproject.repository

import android.content.Context
import com.example.courseproject.database.AppDatabase
import com.example.courseproject.firebase.FirebaseHelper

class Repository private constructor( private var  firebaseHelper:FirebaseHelper, private var  database: AppDatabase){

    companion object{
        @Volatile
        private var INSTANCE: Repository? = null
        fun getInstance(context: Context):Repository{
            synchronized(this) {
                var instance = INSTANCE
                if(instance == null){
                    instance = Repository(FirebaseHelper(), AppDatabase.getInstance(context))
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
}