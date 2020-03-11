package com.example.courseproject.debts

import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.courseproject.database.Debts
import com.example.courseproject.databinding.DebtsListItemBinding

class DebtsAdapter(val clickListener: DebtClickListener):
    ListAdapter<Debts, DebtsAdapter.ViewHolder>(DiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }


    class ViewHolder private constructor(val binding: DebtsListItemBinding)
        :RecyclerView.ViewHolder(binding.root){

        fun bind(item: Debts, clickListener: DebtClickListener){
            binding.debt = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup):ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding
                        = DebtsListItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class DebtClickListener(val clickListener: (debt: Debts) -> Unit){
    fun onClick(debt: Debts) = clickListener(debt)
}

private class DiffCallback: DiffUtil.ItemCallback<Debts>(){
    override fun areItemsTheSame(oldItem: Debts, newItem: Debts): Boolean {
        return oldItem.Id == newItem.Id
    }

    override fun areContentsTheSame(oldItem: Debts, newItem: Debts): Boolean {
        return oldItem == newItem
    }
}