package com.example.courseproject.debts

import android.widget.EditText
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.courseproject.log_in.LogInViewModel

@BindingAdapter("nameFocus")
fun EditText.setNameFocusListener(item: DebtViewModel) {
    setOnFocusChangeListener { _, focused ->
        if(!focused){
            item.checkName()
        }
    }
}

@BindingAdapter("moneyFocus")
fun EditText.setMoneyFocusListener(item: DebtViewModel) {
    setOnFocusChangeListener { _, focused ->
        if(!focused){
            item.checkMoney()
        }
    }
}

@BindingAdapter("moneyText")
fun TextView.setMoneyTxt(money: Float) {
    this.text = money.toString() + "BYN"
}