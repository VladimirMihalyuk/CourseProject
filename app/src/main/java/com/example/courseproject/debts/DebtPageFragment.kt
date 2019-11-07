package com.example.courseproject.debts


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController

import com.example.courseproject.R
import com.example.courseproject.database.Debts
import com.example.courseproject.databinding.FragmentDebtPageBinding
import com.example.courseproject.repository.Repository

/**
 * A simple [Fragment] subclass.
 */
class DebtPageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding:FragmentDebtPageBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_debt_page, container, false)

        val repository = Repository.getInstance(requireNotNull(this.activity).application)
        val viewModelFactory = DebtViewModelFactory(repository)
        val viewModel = activity?.run {
            ViewModelProviders.of(this, viewModelFactory)[DebtViewModel::class.java]
        }?: throw Exception("Invalid Activity")
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val isMine: Boolean = arguments?.getBoolean(KEY_TEXT) ?: false
        Log.d("WTF", "$isMine")

        val adapter = DebtsAdapter(DebtClickListener{ debt: Debts -> repository.updateDebt(debt)})
        binding.recyclerView.adapter = adapter


        if(isMine){
            viewModel.allMineDebts.observe(this, Observer { list ->
                list?.let{
                    adapter.submitList(list)
                }
            })
        } else {
            viewModel.allNotMineDebts.observe(this, Observer { list ->
                list?.let{
                    adapter.submitList(list)
                }
            })
        }


        binding.addButton.setOnClickListener{
            findNavController().navigate(DebtsFragmentDirections.actionDeptsFragmentToAddDeptDetailsFragment())
        }

        return binding.root
    }


    companion object {
        private val KEY_TEXT = "special_text"

        fun newInstance(isMine: Boolean): DebtPageFragment {
            val fragment = DebtPageFragment()
            val args = Bundle()
            args.putBoolean(KEY_TEXT, isMine)
            fragment.arguments = args
            return fragment
        }
    }

}
