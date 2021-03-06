package com.example.courseproject.accounting.views


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.courseproject.App
import com.example.courseproject.R
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.courseproject.accounting.*
import com.example.courseproject.accounting.view_model.AccountingViewModel
import com.example.courseproject.accounting.view_model.AccountingViewModelFactory
import com.example.courseproject.database.AccountingItemInfo
import com.example.courseproject.databinding.FragmentAccountigBinding
import com.example.courseproject.repository.Repository


val ITEM_WIDTH = 80

class AccountigFragment : Fragment() {
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(App.prefs?.idClient == null){
            findNavController().
                navigate(AccountigFragmentDirections.actionAccountigFragmentToLogInFlowActivity())
        }

    }




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentAccountigBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_accountig, container, false)

        binding.lifecycleOwner = this


        val repository = Repository.getInstance(requireNotNull(this.activity).application)
        val viewModelFactory =
            AccountingViewModelFactory(repository)
        val viewModel = activity?.run {
            ViewModelProviders.of(this, viewModelFactory)[AccountingViewModel::class.java]
        }?: throw Exception("Invalid Activity")

        binding.viewModel = viewModel

        val displayMetrics = context?.resources?.displayMetrics
        var dpWidth = 0F
        if(displayMetrics != null){
            dpWidth = displayMetrics.widthPixels / displayMetrics.density
        }

        val onClick =
            AccountingInfoClickListener { item: AccountingItemInfo ->
                AccountingViewModel.itemInfo = item
                findNavController()
                    .navigate(AccountigFragmentDirections
                        .actionAccountigFragmentToAccountingItemsListFragment())
            }

        val costsAdapter = AdapterForAccountingItems(onClick)
        binding.costs.adapter = costsAdapter
        binding.costs.layoutManager = GridLayoutManager(activity, (dpWidth/ ITEM_WIDTH).toInt())
        viewModel.costsItemList.observe(this, Observer {list ->
           list?.let{
               costsAdapter.submitList(list)
           }
        })

        val incomeAdapter = AdapterForAccountingItems(onClick)
        binding.income.adapter = incomeAdapter
        viewModel.incomeItemList.observe(this, Observer { list ->
            list?.let{
                incomeAdapter.submitList(list)
            }
        })
        return binding.root
    }
}
