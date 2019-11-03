package com.example.courseproject.log_up

import android.widget.EditText
import androidx.databinding.BindingAdapter
import com.example.courseproject.log_in.EmailValidator

@BindingAdapter("emailFocus")
fun EditText.setEmailFocusListener(item: LogUpViewModel) {
    setOnFocusChangeListener { _, focused ->
        if(!focused){
            item.checkEmail()
        }
    }
}

@BindingAdapter("passwordFocus")
fun EditText.setPasswordFocusListener(item: LogUpViewModel) {
    setOnFocusChangeListener { _, focused ->
        if(!focused){
            item.checkPassword()
        }
    }
}

@BindingAdapter("confirmPasswordFocus")
fun EditText.setConfirmPasswordFocusListener(item: LogUpViewModel) {
    setOnFocusChangeListener { _, focused ->
        if(!focused){
            item.checkConfirmPassword()
        }
    }
}
