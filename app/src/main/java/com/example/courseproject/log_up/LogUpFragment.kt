package com.example.courseproject.log_up


import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.courseproject.R
import com.example.courseproject.databinding.FragmentLogUpBinding
import com.example.courseproject.log_in.LogInViewModel
import com.example.courseproject.log_in.LoginFragmentDirections
import com.example.courseproject.repository.Repository
import com.google.android.material.snackbar.Snackbar

/**
 * A simple [Fragment] subclass.
 */
class LogUpFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentLogUpBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_log_up, container, false)

        val repository = Repository.getInstance(requireNotNull(this.activity).application)
        val viewModelFactory = LogUpViewModelFactory(repository)
        val viewModel = ViewModelProviders.of(this, viewModelFactory).get(LogUpViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this



        viewModel.isEmailValid.observe(this, Observer { flag ->
            if(flag){
                binding.emailLayout.isErrorEnabled = false
            } else {
                binding.emailLayout.error = "Некоректный адресс электронной почты"
            }
        })

        viewModel.isPasswordValid.observe(this , Observer { flag ->
            if(flag){
                binding.passwordlLayout.isErrorEnabled = false
            } else {
                binding.passwordlLayout.error = "Пароль должен быть не меньше 8 символов"
            }
        })

        viewModel.isConfirmPasswordValid.observe(this, Observer {flag ->
            if(flag){
                binding.confirmPasswordlLayout.isErrorEnabled = false
            } else {
                binding.confirmPasswordlLayout.error = "Введённые пароли не совпадают"
            }

        })


        viewModel.errorCode.observe(this, Observer {code ->
            var stringId = ""
            when(code){
                0 -> stringId = "Вы успешно зарегистрировались"
                1 -> stringId = "Пользователь с таким адресом электронной почты уже зарегистрирован"
                2 -> stringId = "Некоректные данные"
            }
            val mySnackbar = Snackbar.make(view!!, stringId, Snackbar.LENGTH_LONG)
            mySnackbar.show()

        })


        viewModel.isLoading.observe(this, Observer {isLoading ->
            if(isLoading){
                activity?.window?.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                binding.progressCircular.visibility = VISIBLE

            } else {
                activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                binding.progressCircular.visibility = GONE

            }
        })
        return binding.root
    }
}
