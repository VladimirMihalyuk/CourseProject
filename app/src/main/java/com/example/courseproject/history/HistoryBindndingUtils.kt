package com.example.courseproject.history

import android.graphics.Color
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.courseproject.database.AccountingItem
import com.example.courseproject.database.HistoryItem

@BindingAdapter("historyMoneyItem")
fun TextView.setMoneyItem(item: HistoryItem?){
    item?.AmountOfMoney?.let{
        this.text = item.AmountOfMoney.toString()
        if(item.TypeOfItem == 1)
            this.setTextColor(Color.GREEN)
        else
            this.setTextColor(Color.RED)
    }
}