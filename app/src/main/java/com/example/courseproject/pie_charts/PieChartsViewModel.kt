package com.example.courseproject.pie_charts

import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.courseproject.App
import com.example.courseproject.repository.Repository
import com.github.mikephil.charting.data.PieEntry

class PieChartsViewModel(val repository: Repository) : ViewModel()  {
    private var date = (System.currentTimeMillis()/ 86400000) * 86400000

    private val forWeek
            = repository.gtCostsForPieChartForWeek(App.prefs?.idClient ?: "", date)

    private val forMonth
            = repository.getCostsForPieChartForMonth(App.prefs?.idClient ?: "", date)

    private val forAllTime
            = repository.getCostsForPieChartForAllTime(App.prefs?.idClient ?: "")


    val week = Transformations.map(forWeek){ list ->
        list.map{ PieEntry(it.Value, it.Type) }
    }

    val month = Transformations.map(forMonth){list ->
        list.map{ PieEntry(it.Value, it.Type) }
    }

    val allTime = Transformations.map(forAllTime){list ->
        list.map{ PieEntry(it.Value, it.Type) }
    }
}