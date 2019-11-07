package com.example.courseproject.accounting


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders

import com.example.courseproject.R
import com.example.courseproject.repository.Repository

/**
 * A simple [Fragment] subclass.
 */
class AccountingItemsListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_accounting_items_list,
            container, false)

        val repository = Repository.getInstance(requireNotNull(this.activity).application)
        val viewModelFactory = AccountingViewModelFactory(repository)
        val viewModel = activity?.run {
            ViewModelProviders.of(this, viewModelFactory)[AccountingViewModel::class.java]
        }?: throw Exception("Invalid Activity")

        val item = AccountingItemsListFragmentArgs.fromBundle(arguments!!).itemInfo

        if(item.)



        return view
    }


}
