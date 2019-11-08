package com.example.courseproject.accounting


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.courseproject.R
import com.example.courseproject.databinding.FragmentAccountingItemsListBinding
import com.example.courseproject.repository.Repository

/**
 * A simple [Fragment] subclass.
 */
class AccountingItemsListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding:FragmentAccountingItemsListBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_accounting_items_list,
            container, false)


        val repository = Repository.getInstance(requireNotNull(this.activity).application)
        val viewModelFactory = AccountingViewModelFactory(repository)
        val viewModel = activity?.run {
            ViewModelProviders.of(this, viewModelFactory)[AccountingViewModel::class.java]
        }?: throw Exception("Invalid Activity")



        val adapter = AdapterForListOfAccountingItems()
        binding.list.adapter = adapter
        val item = AccountingViewModel.itemInfo
        if(item.AccountingType ==1 ){
            viewModel.incomeItemsMap.observe(this, Observer { map ->
                map?.let{
                   adapter.submitList(map.get(Triple(item.Type, item.Icon, item.IdOfICType)))
                }
            })
        }else{
            viewModel.costsItemsMap.observe(this, Observer { map ->
                map?.let{
                    adapter.submitList(map.get(Triple(item.Type, item.Icon, item.IdOfICType)))
                }
            })
        }

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(AccountingItemsListFragmentDirections.actionAccountingItemsListFragmentToAddAccountingItemFragment())
        }



        return binding.root
    }


}
