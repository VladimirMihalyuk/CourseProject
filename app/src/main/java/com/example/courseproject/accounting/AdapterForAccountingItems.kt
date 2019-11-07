package com.example.courseproject.accounting


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.courseproject.database.AccountingItemInfo
import com.example.courseproject.databinding.AccountingItemBinding
import com.example.courseproject.databinding.DebtsListItemBinding
import com.example.courseproject.debts.DebtsAdapter


class AdapterForAccountingItems:
    ListAdapter<AccountingItemInfo, AdapterForAccountingItems.ViewHolder>(DiffCallback()){

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }



    class ViewHolder private constructor(val binding: AccountingItemBinding):
        RecyclerView.ViewHolder(binding.root){

        fun bind(item: AccountingItemInfo){
            binding.item = item
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup): ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = AccountingItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}





private class DiffCallback: DiffUtil.ItemCallback<AccountingItemInfo>(){
    override fun areItemsTheSame(oldItem: AccountingItemInfo, newItem: AccountingItemInfo): Boolean {
        return oldItem.IdOfICType == newItem.IdOfICType
    }

    override fun areContentsTheSame(oldItem: AccountingItemInfo, newItem: AccountingItemInfo): Boolean {
        return oldItem == newItem
    }
}