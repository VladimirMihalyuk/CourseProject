package com.example.courseproject.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.courseproject.accounting.AdapterForListOfAccountingItems
import com.example.courseproject.database.HistoryItem
import com.example.courseproject.databinding.HistoryItemBinding

class HistoryAdapter():
        ListAdapter<HistoryItem, HistoryAdapter.ViewHolder>(DiffCallbackHistory()){

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        return ViewHolder.from(parent)
    }



    class ViewHolder private constructor(val binding:HistoryItemBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bind(item: HistoryItem){
            binding.item = item
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = HistoryItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}


private class DiffCallbackHistory: DiffUtil.ItemCallback<HistoryItem>(){
    override fun areItemsTheSame(oldItem: HistoryItem, newItem: HistoryItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: HistoryItem, newItem: HistoryItem): Boolean {
        return oldItem == newItem
    }
}