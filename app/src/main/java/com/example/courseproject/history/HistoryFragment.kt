package com.example.courseproject.history


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.example.courseproject.R
import com.example.courseproject.repository.Repository
import kotlinx.android.synthetic.main.fragment_history.view.*

/**
 * A simple [Fragment] subclass.
 */
class HistoryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_history, container, false)

        val repository = Repository.getInstance(requireNotNull(this.activity).application)
        val viewModelFactory = HistoryViewModelFactory(repository)
        val viewModel = ViewModelProviders.of(this, viewModelFactory).
            get(HistoryViewModel::class.java)

        val adapter = HistoryAdapter()
        view.history.adapter = adapter
        viewModel.historyItems.observe(this, Observer {list ->
            list?.let {
                //list.sortBy { it.DateOfItem < it.DateOfItem}

                adapter.submitList(list)
                Log.d("WTF", "$list")
            }
        })

        return view
    }


}
