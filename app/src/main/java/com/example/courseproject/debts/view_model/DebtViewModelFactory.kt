package com.example.courseproject.debts.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.courseproject.repository.Repository


class DebtViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DebtViewModel::class.java)) {
            return DebtViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
