package com.example.courseproject.accounting

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter

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