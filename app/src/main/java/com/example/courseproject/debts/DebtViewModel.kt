package com.example.courseproject.debts

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.courseproject.App
import com.example.courseproject.repository.Repository


class DebtViewModel(val repository: Repository) : ViewModel() {
    val allMineDebts = repository.getAllMineDebts(App.prefs?.idClient ?: "")
    val allNotMineDebts = repository.getAllNotMineDebts(App.prefs?.idClient ?: "")

    val name = MutableLiveData<String>()
    val money = MutableLiveData<String>()

    private val _nameError = MutableLiveData<Boolean>()
    val nameError:LiveData<Boolean>
        get() = _nameError

    private val _moneyError = MutableLiveData<Boolean>()
    val moneyError: LiveData<Boolean>
        get() = _moneyError

    init{
        Log.d("WTF", "INIT")
        repository.loadAllDebts(App.prefs?.idClient ?: "" )
    }


    fun checkName(){
        _nameError.value = name.value?.length ?: 0 != 0
    }

    private var moneyValue: Float = 0F
    fun checkMoney(){
        if( money.value?.length ?: 0 != 0){
            try{
                moneyValue = money.value?.toFloat() ?: 0F
                _moneyError.value = true
            } catch (e : NumberFormatException){
                _moneyError.value = false
            }
        } else {
            _moneyError.value = false
        }

    }


    private var _addErrorCode = MutableLiveData<Int>()
    val addErrorCode: LiveData<Int>
        get() = _addErrorCode

    fun addDebt(){
        checkMoney()
        checkName()
        if(_nameError.value == true && _moneyError.value == true){
            startLoading()
            repository.addDebt(App.prefs?.idClient?:"", moneyValue,
                if(isMine)  1 else 0, name.value ?: "", 1){finishCallback()}
        } else {
            _addErrorCode.value = 1 //WRONG fields value
        }

    }


    private var _isLoding = MutableLiveData<Boolean>()
    val isLoding: LiveData<Boolean>
        get() = _isLoding

    private fun startLoading(){
        _isLoding.value = true
    }

    private fun finishCallback(){
        _isLoding.value = false
        _addErrorCode.value = 0
    }

    companion object{
        private var isMine = true
        fun setIsMine(isMine: Boolean){
            this.isMine = isMine
        }

        fun getIsMine():Boolean = isMine

    }
}