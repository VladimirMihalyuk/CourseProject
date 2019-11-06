package com.example.courseproject.debts


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.courseproject.R
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_debts.view.*

/**
 * A simple [Fragment] subclass.
 */
class DebtsFragment : Fragment() {

    private val list = listOf(true, false)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_debts, container, false)



        val pagerAdapter = ScreenSlidePagerAdapter(this)
        view.viewPager2.adapter = pagerAdapter

        view.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                DebtViewModel.setIsMine(list[position])
            }
        })

        TabLayoutMediator(view.tabLayout, view.viewPager2) { tab, position ->
            tab.text = when(position) {
                0 -> "Мои долги"
                else -> "Должен я"
            }
        }.attach()

        return view
    }

    private inner class ScreenSlidePagerAdapter(fa: Fragment) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = list.size

        override fun createFragment(position: Int): Fragment = DebtPageFragment.newInstance(list[position])

    }

}
