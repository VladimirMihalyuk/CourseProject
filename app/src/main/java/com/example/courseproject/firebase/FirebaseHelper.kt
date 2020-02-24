package com.example.courseproject.firebase



import android.util.Log
import com.example.courseproject.App
import com.example.courseproject.database.Costs
import com.example.courseproject.database.Debts
import com.example.courseproject.database.Income
import com.example.courseproject.database.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class FirebaseHelper {
    private val mAuth:FirebaseAuth = FirebaseAuth.getInstance()
    private val fireDatabase = FirebaseDatabase.getInstance().reference




    fun signIn(email: String, password: String, completionListener: (isSuccessful: Boolean) -> Unit){
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            completionListener(task.isSuccessful)
            if(task.isSuccessful){
                Log.d("WTF", "${task.result?.user?.uid}")
                App.prefs?.idClient = task.result?.user?.uid
            }
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


    fun signOt(){
        App.prefs?.idClient = null
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

    fun addDebt(userId: String, money: Float, isMineDept: Int, name: String, isActive: Int):Debts{
        val id = fireDatabase.child("debts").child(userId).push().key ?: ""
        val debt  = Debts(id, userId, money, isMineDept, name, isActive)
        fireDatabase.child("debts").child(userId).child(id).setValue(debt)
        return debt
    }

    fun addIncome(userId: String, income:Income): Income {
        val id = fireDatabase.child("income").child(userId).push().key ?: ""
        income.Id = id
        fireDatabase.child("income").child(userId).child(id).setValue(income)
        return income
    }

    fun addCost(userId:String, cost: Costs): Costs {
        val id = fireDatabase.child("costs").child(userId).push().key ?: ""
        cost.Id = id
        fireDatabase.child("costs").child(userId).child(id).setValue(cost)
        return cost
    }

    fun loadAllDebts(userId: String, callback: (list: MutableList<Debts>) -> Unit){
        val menu: MutableList<Debts> = mutableListOf()
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.mapNotNullTo(menu) { it.getValue<Debts>(Debts::class.java) }
                callback(menu)
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        }
        fireDatabase.child("debts").child(userId).
            addValueEventListener(postListener)
    }

    fun loadAllIncome(userId: String, callback: (list: MutableList<Income>) -> Unit){
        val menu: MutableList<Income> = mutableListOf()
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.mapNotNullTo(menu) { it.getValue<Income>(Income::class.java) }
                callback(menu)
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        }
        fireDatabase.child("income").child(userId).
            addValueEventListener(postListener)
    }

    fun loadAllCosts(userId: String, callback: (list: MutableList<Costs>) -> Unit){
        val menu: MutableList<Costs> = mutableListOf()
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.mapNotNullTo(menu) { it.getValue<Costs>(Costs::class.java) }
                callback(menu)
            }

            override fun onCancelled(databaseError: DatabaseError) { }
        }
        fireDatabase.child("costs").child(userId).
            addValueEventListener(postListener)
    }

    fun updateDebt(debt: Debts){
        fireDatabase.child("debts").child(debt.UserId).child(debt.Id).setValue(debt)
    }


}