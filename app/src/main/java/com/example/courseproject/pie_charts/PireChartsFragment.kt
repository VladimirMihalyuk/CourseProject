package com.example.courseproject.pie_charts


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2

import com.example.courseproject.R
import com.example.courseproject.debts.DebtPageFragment
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_debts.view.*

/**
 * A simple [Fragment] subclass.
 */
class PireChartsFragment : Fragment() {

    private val list = listOf(1 , 2, 3)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pire_charts, container, false)

        val pagerAdapter = ScreenSlidePagerAdapter(this)
        view.viewPager2.adapter = pagerAdapter


        view.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {

            }
        })

        TabLayoutMediator(view.tabLayout, view.viewPager2) { tab, position ->
            tab.text = when(position) {
                0 -> "За неделю"
                1 -> "За месяц"
                else -> "За всё время"
            }
        }.attach()

        return view
    }


    private inner class ScreenSlidePagerAdapter(fa: Fragment) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = list.size

        override fun createFragment(position: Int): Fragment
                = PieChartPageFragment.newInstance(list[position])

    }
}
