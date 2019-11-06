package com.example.courseproject.debts

import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.courseproject.database.Depts
import com.example.courseproject.databinding.DebtsListItemBinding

class DebtsAdapter: ListAdapter<Depts, DebtsAdapter.ViewHolder>(DiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }


    class ViewHolder private constructor(val binding: DebtsListItemBinding)
        :RecyclerView.ViewHolder(binding.root){

        fun bind(item: Depts){
            binding.debt = item
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup):ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = DebtsListItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

private class DiffCallback: DiffUtil.ItemCallback<Depts>(){
    override fun areItemsTheSame(oldItem: Depts, newItem: Depts): Boolean {
        return oldItem.Id == newItem.Id
    }

    override fun areContentsTheSame(oldItem: Depts, newItem: Depts): Boolean {
        return oldItem == newItem
    }
}