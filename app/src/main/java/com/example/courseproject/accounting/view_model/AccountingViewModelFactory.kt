package com.example.courseproject.accounting.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.courseproject.repository.Repository

class AccountingViewModelFactory(private val repository: Repository): ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AccountingViewModel::class.java)) {
            return AccountingViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}