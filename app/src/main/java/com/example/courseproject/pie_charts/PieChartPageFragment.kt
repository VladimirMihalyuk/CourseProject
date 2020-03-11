package com.example.courseproject.pie_charts


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import android.graphics.Color
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.courseproject.R
import com.example.courseproject.repository.Repository
import com.github.mikephil.charting.charts.PieChart
import kotlinx.android.synthetic.main.fragment_pie_chart_page.view.*





/**
 * A simple [Fragment] subclass.
 */
class PieChartPageFragment : Fragment() {

    private val MyColors = intArrayOf(
        Color.rgb(255, 115, 0),
        Color.rgb(255, 236, 1),
        Color.rgb(215, 243, 11),
        Color.rgb(82, 215, 38),
        Color.rgb(27, 169, 47),
        Color.rgb(45, 203, 118),
        Color.rgb(36, 215, 173),
        Color.rgb(119, 221, 223),
        Color.rgb(0, 127, 211)
    )


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pie_chart_page, container, false)

        val repository = Repository.getInstance(requireNotNull(this.activity).application)
        val viewModelFactory = PieChartsViewModelFactory(repository)
        val viewModel = ViewModelProviders.of(this, viewModelFactory).
            get(PieChartsViewModel::class.java)

        val number: Int = arguments?.getInt(KEY_TEXT) ?: 1

        val pieChart = view.pieChart


        when(number){
            1 -> {
                viewModel.week.observe(this, Observer { list ->
                    drawChart(pieChart, list)
                })
            }
            2 -> {
                viewModel.month.observe(this, Observer { list ->
                    drawChart(pieChart, list)
                })
            }
            3 -> {
                viewModel.allTime.observe(this, Observer { list ->
                    drawChart(pieChart, list)
                })
            }
        }


        return view
    }

    private fun drawChart(pieChart: PieChart, list: List<PieEntry>) {
        pieChart.setUsePercentValues(true)
        pieChart.isDrawHoleEnabled = true
        pieChart.transparentCircleRadius = 58f
        pieChart.holeRadius = 58f

        val dataSet = PieDataSet(list,"")
        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter())

        val legend = pieChart.legend
        legend.isEnabled = false
        pieChart.description.isEnabled = false
        dataSet.setColors(*MyColors)
        data.setValueTextSize(12f)
        data.setValueTextColor(Color.DKGRAY)
        pieChart.data = data
        pieChart.invalidate()
    }

    companion object {
        private val KEY_TEXT = "special_text"

        fun newInstance(number: Int): PieChartPageFragment {
            val fragment = PieChartPageFragment()
            val args = Bundle()
            args.putInt(KEY_TEXT, number)
            fragment.arguments = args
            return fragment
        }
    }
}
