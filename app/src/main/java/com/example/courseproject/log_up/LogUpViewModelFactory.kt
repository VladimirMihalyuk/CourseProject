package com.example.courseproject.log_up

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.courseproject.log_in.LogInViewModel
import com.example.courseproject.repository.Repository

class LogUpViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LogUpViewModel::class.java)) {
                return LogUpViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
}