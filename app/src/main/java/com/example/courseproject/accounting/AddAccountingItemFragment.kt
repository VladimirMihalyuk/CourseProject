package com.example.courseproject.accounting


import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.Observer
import com.example.courseproject.R
import com.example.courseproject.databinding.FragmentAddAccountingItemBinding
import com.example.courseproject.repository.Repository
import com.google.android.material.snackbar.Snackbar
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class AddAccountingItemFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentAddAccountingItemBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_add_accounting_item, container, false)


        val repository = Repository.getInstance(requireNotNull(this.activity).application)
        val viewModelFactory = AccountingViewModelFactory(repository)
        val viewModel = activity?.run {
            ViewModelProviders.of(this, viewModelFactory)[AccountingViewModel::class.java]
        }?: throw Exception("Invalid Activity")

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.descriptionCorrect.observe(this, Observer {isCorrect ->
            if(isCorrect){
                binding.detailsLayout.isErrorEnabled = false
            }else{
                binding.detailsLayout.error = "Введите описание"
            }
        })

        viewModel.moneyCorrect.observe(this , Observer { isCorrect ->
            if(isCorrect){
                binding.moneyLayout.isErrorEnabled = false
            } else {
                binding.moneyLayout.error = "Неправильные данные"
            }
        })


        binding.datePicker.setOnClickListener{
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(view!!.context,
                DatePickerDialog.OnDateSetListener {
                        view, year, monthOfYear, dayOfMonth ->
                // Display Selected date in Toast
                viewModel.setDate(dayOfMonth, monthOfYear + 1, year)


            }, year, month, day)
            dpd.show()
        }

        viewModel.isLoading.observe(this, Observer {isLoading ->
            if(isLoading){
                activity?.window?.setFlags(
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                binding.progressCircular.visibility = View.VISIBLE
                binding.loadLayout.visibility = View.VISIBLE

            } else {
                activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                binding.progressCircular.visibility = View.GONE
                binding.loadLayout.visibility = View.INVISIBLE

            }
        })

        viewModel.finishStatus.observe(this, Observer {success ->
            var stringId = ""
            stringId = if(success){
                "Запись успешно добавлена"
            }else {
                "Некорректные данные"
            }
            val mySnackbar = Snackbar.make(view!!, stringId, Snackbar.LENGTH_LONG)
            mySnackbar.show()

        })

        return binding.root
    }


}
