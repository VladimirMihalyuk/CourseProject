package com.example.courseproject.log_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.courseproject.repository.Repository

class LogInViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LogInViewModel::class.java)) {
            return LogInViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}