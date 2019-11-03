package com.example.courseproject.log_up

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.courseproject.log_in.EmailValidator
import com.example.courseproject.repository.Repository

class LogUpViewModel(val repository: Repository) : ViewModel() {
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val confirmPassword = MutableLiveData<String>()

    val isEmailValid = MutableLiveData<Boolean>()
    val isPasswordValid = MutableLiveData<Boolean>()
    val isConfirmPasswordValid = MutableLiveData<Boolean>()

    private val _errorCode = MutableLiveData<Int>()
    val errorCode: LiveData<Int>
        get() = _errorCode

    fun logUp(){
        checkConfirmPassword()
        checkEmail()
        checkPassword()
        if(isEmailValid. value == true &&  isPasswordValid.value == true &&
            isConfirmPasswordValid.value == true){
            startLoading()
            repository.logUp(email.value ?: "",
                password.value ?: "") { flag -> getResult(flag)}
        } else {
            _errorCode.value = 2 //wrong data
        }
    }

    private fun getResult(result: Boolean){
        stopLoading()
        if(result){
            _errorCode.value = 0 //successes
        } else {
            _errorCode.value = 1 //such user already exist
        }

    }

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private fun startLoading(){
        _isLoading.value = true
    }

    private fun stopLoading(){
        _isLoading.value = false
    }

    fun checkEmail(){
        isEmailValid.value = EmailValidator.isValid(email.value ?: "")
    }

    fun checkPassword(){
        isPasswordValid.value = password.value?.length ?: 0 >= 8
    }

    fun checkConfirmPassword(){
        isConfirmPasswordValid.value = password.value?.equals(confirmPassword.value)
    }

}