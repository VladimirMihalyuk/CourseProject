package com.example.courseproject.log_in


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.courseproject.repository.Repository

class LogInViewModel(val repository: Repository) : ViewModel(){
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    val isEmailValid = MutableLiveData<Boolean>()
    val isPasswordValid = MutableLiveData<Boolean>()


    fun signIn(){
        checkEmail()
        checkPassword()
        if(isEmailValid.value == true && isPasswordValid.value == true){
            repository.logIn(email.value ?: "", password.value ?: "")
            { value -> finishedEvent(value)}
        } else {
            _errorCode.value = 2 //wrong fields
        }

    }


    private val _errorCode = MutableLiveData<Int>()
    val errorCode: LiveData<Int>
        get() = _errorCode
    private fun finishedEvent(result: Boolean){
        if(result){
            _errorCode.value = 0//everything ok
        } else {
            _errorCode.value = 1//such user does not exist
        }
    }



    private val _moveToRegistrationFragmentEvent = MutableLiveData<Boolean>()
    val moveToRegistrationFragmentEvent: LiveData<Boolean>
        get() = _moveToRegistrationFragmentEvent
    fun moveToRegistrationFragment(){
        _moveToRegistrationFragmentEvent.value = true
    }

    fun resetEvent(){
        _moveToRegistrationFragmentEvent.value = false
    }

    fun checkEmail(){
        isEmailValid.value = EmailValidator.isValid(email.value ?: "")
    }

    fun checkPassword(){
        isPasswordValid.value = password.value?.length ?: 0 >= 8
    }
}
