package com.example.courseproject.debts


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController

import com.example.courseproject.R
import kotlinx.android.synthetic.main.fragment_debt_page.view.*

/**
 * A simple [Fragment] subclass.
 */
class DebtPageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_debt_page, container, false)


        view.addButton.setOnClickListener{
            findNavController().navigate(DebtsFragmentDirections.actionDeptsFragmentToAddDeptDetailsFragment())
        }

        return view
    }

}
