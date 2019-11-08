package com.example.courseproject.history

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.courseproject.App
import com.example.courseproject.database.HistoryItem
import com.example.courseproject.prefs
import com.example.courseproject.repository.Repository
import kotlinx.coroutines.flow.combine

class HistoryViewModel(val repository: Repository) : ViewModel()  {
    private val income =
        repository.getAllIncomeForHistory(App.prefs?.idClient ?: "")
    private val costs
            = repository.getAllCostsForHistory(App.prefs?.idClient ?: "")

    private val incomeItems = Transformations.map(income){list ->
        list.map{HistoryItem(it)}
    }

    private val costItems = Transformations.map(costs){ list->
        list.map { HistoryItem(it) }
    }


    val historyItems = MediatorLiveData < MutableList<HistoryItem>>()

    init {
        historyItems.addSource(incomeItems){
            historyItems.value?.addAll(it)
        }
        historyItems.addSource(costItems){
            historyItems.value?.addAll(it)
        }
    }
}