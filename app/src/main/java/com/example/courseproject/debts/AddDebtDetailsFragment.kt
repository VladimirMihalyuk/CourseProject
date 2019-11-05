package com.example.courseproject.debts


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.example.courseproject.R
import com.example.courseproject.databinding.FragmentAddDebtDetailsBinding
import com.example.courseproject.log_up.LogUpViewModel
import com.example.courseproject.repository.Repository
import com.google.android.material.snackbar.Snackbar

/**
 * A simple [Fragment] subclass.
 */
class AddDebtDetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding:FragmentAddDebtDetailsBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_add_debt_details, container, false)

        val repository = Repository.getInstance(requireNotNull(this.activity).application)
        val viewModelFactory = DebtViewModelFactory(repository)
        val viewModel = ViewModelProviders.of(this, viewModelFactory)[DebtViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.isLoding.observe(this, Observer { isLoading ->
            if(isLoading){
                activity?.window?.setFlags(
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                binding.progressCircular.visibility = View.VISIBLE
                binding.loadingLayout.visibility = View.VISIBLE
            } else {
                activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                binding.progressCircular.visibility = View.GONE
                binding.loadingLayout.visibility = View.INVISIBLE
            }
        })

        viewModel.moneyError.observe(this, Observer { correct ->
            if(correct){
                binding.moneyLayout.isErrorEnabled = false
            } else {
                binding.moneyLayout.error = "Некорректное значение суммы денег"
            }
        })

        viewModel.nameError.observe(this, Observer { correct ->
            if(correct){
                binding.nameLayout.isErrorEnabled = false
            } else {
                binding.nameLayout.error = "Имя не должно быть пустым"
            }
        })

        viewModel.addErrorCode.observe(this, Observer { errorCode ->
            var stringId = ""
            when(errorCode){
                0 -> stringId = "Вы успешно добавили запись"
                1 -> stringId = "Неправильные значения полей"
            }
            val mySnackbar = Snackbar.make(view!!, stringId, Snackbar.LENGTH_LONG)
            mySnackbar.show()
        })


        return binding.root
    }


}
