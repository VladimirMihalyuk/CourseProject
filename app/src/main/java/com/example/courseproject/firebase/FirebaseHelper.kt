package com.example.courseproject.firebase


import com.google.firebase.auth.FirebaseAuth



class FirebaseHelper {
    private val mAuth:FirebaseAuth = FirebaseAuth.getInstance()

    fun signIn(email: String, password: String){

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            task.isSuccessful
        }


    }

    fun signUp(email: String, password: String){
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            task.isSuccessful
        }
    }


    fun signOt(){
        mAuth.signOut()
    }
}