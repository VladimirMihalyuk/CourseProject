package com.example.courseproject.log_in

import android.widget.EditText
import androidx.databinding.BindingAdapter

@BindingAdapter("emailFocus")
fun EditText.setEmailFocusListener(item: LogInViewModel) {
    setOnFocusChangeListener { _, focused ->
        if(!focused){
            item.checkEmail()
        }
    }
}

@BindingAdapter("passwordFocus")
fun EditText.setPasswordFocusListener(item: LogInViewModel) {
    setOnFocusChangeListener { _, focused ->
        if(!focused){
            item.checkPassword()
        }
    }
}