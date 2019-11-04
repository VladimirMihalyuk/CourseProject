package com.example.courseproject.depts


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.courseproject.R
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_depts.view.*

/**
 * A simple [Fragment] subclass.
 */
class DeptPageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_dept_page, container, false)

        val isMine: Boolean? = arguments?.getBoolean(KEY_TEXT)
        Log.d("WTF", "$isMine")


        return view
    }


    companion object {
        private val KEY_TEXT = "special_text"

        fun newInstance(isMine: Boolean): DeptPageFragment {
            val fragment = DeptPageFragment()
            val args = Bundle()
            args.putBoolean(KEY_TEXT, isMine)
            fragment.arguments = args
            return fragment
        }
    }
}
