package com.example.courseproject.accounting

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.courseproject.database.AccountingItem
import com.example.courseproject.databinding.AccountingListItemBinding

class AdapterForListOfAccountingItems :
    ListAdapter<AccountingItem, AdapterForListOfAccountingItems.ViewHolder>(DiffCallbackItems()){

    override fun onBindViewHolder(holder: AdapterForListOfAccountingItems.ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterForListOfAccountingItems.ViewHolder {
        return AdapterForListOfAccountingItems.ViewHolder.from(parent)
    }


    class ViewHolder private constructor(val binding:AccountingListItemBinding):
            RecyclerView.ViewHolder(binding.root){
        fun bind(item: AccountingItem){
            binding.item = item
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup): AdapterForListOfAccountingItems.ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = AccountingListItemBinding.inflate(layoutInflater, parent, false)
                return AdapterForListOfAccountingItems.ViewHolder(binding)
            }
        }
    }
}

private class DiffCallbackItems: DiffUtil.ItemCallback<AccountingItem>(){
    override fun areItemsTheSame(oldItem: AccountingItem, newItem: AccountingItem): Boolean {
        return oldItem.Id == newItem.Id
    }

    override fun areContentsTheSame(oldItem: AccountingItem, newItem: AccountingItem): Boolean {
        return oldItem == newItem
    }
}