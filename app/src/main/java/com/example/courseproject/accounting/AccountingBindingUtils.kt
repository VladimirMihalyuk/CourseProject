package com.example.courseproject.accounting

import android.graphics.Color
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.courseproject.database.AccountingItem
import java.util.*


@BindingAdapter("image")
fun ImageView.setImage(item: String?) {
    item?.let {
        val resID = context.resources.getIdentifier(item, "drawable", context.packageName)
        setImageResource(resID)
    }
}

@BindingAdapter("money")
fun TextView.setMoney(item: Float? ){
    item?.let{
        this.text = item.toString() + "BYN"
    }
}

@BindingAdapter("date")
fun TextView.setDate(date: Date?){
    date?.let{
        this.text = AccountingViewModel.parseDateToString(date)
    }
}

@BindingAdapter("moneyItem")
fun TextView.setMoneyItem(item: AccountingItem?){
    item?.AmountOfMoney?.let{
        this.text = if(item.AccountingType == 1)"+" else "-" + item.AmountOfMoney.toString()
        if(item.AccountingType == 1)
            this.setTextColor(Color.GREEN)
        else
            this.setTextColor(Color.RED)
    }
}

