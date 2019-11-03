package com.example.courseproject.firebase


import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.ActivityCompat.startActivityForResult
import com.example.courseproject.R
import com.example.courseproject.database.User
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class FirebaseHelper {
    private val mAuth:FirebaseAuth = FirebaseAuth.getInstance()
    private val fireDatabase = FirebaseDatabase.getInstance().reference




    fun signIn(email: String, password: String, completionListener: (isSuccessful: Boolean) -> Unit){
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            getResult(task.isSuccessful)
            completionListener(task.isSuccessful)
        }
    }

    fun signUp(email: String, password: String,  completionListener: (isSuccessful: Boolean) -> Unit){
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->

            try {
                loadUser(task.isSuccessful, task.result?.user?.uid, email, password)
                completionListener(task.isSuccessful)
            }catch (e: Exception){
                completionListener(false)
            }

        }
    }

    fun getResult(result: Boolean){
        Log.d("WTF", "$result")
    }


    fun signOt(){
        mAuth.signOut()
    }

    private fun loadUser(result: Boolean,uid: String?, email: String, password: String){
        if(result){
            addUser(uid, email, password)
        }
    }

    private fun addUser(uid: String?,email: String, password: String){
        uid?.let{
            val user = User(uid, email, password)
            fireDatabase.child("user").child(uid).setValue(user)
            Log.d("FIRE", "$uid")
        }

    }


}