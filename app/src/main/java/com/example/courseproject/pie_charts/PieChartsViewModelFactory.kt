package com.example.courseproject.pie_charts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.example.courseproject.repository.Repository


class PieChartsViewModelFactory(private val repository: Repository): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PieChartsViewModel::class.java)) {
            return PieChartsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
