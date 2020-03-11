package com.example.courseproject.log_in


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
import com.example.courseproject.databinding.FragmentLoginBinding
import com.example.courseproject.firebase.FirebaseHelper
import com.example.courseproject.repository.Repository
import com.google.android.material.snackbar.Snackbar
import java.lang.Exception


/**
 * A simple [Fragment] subclass.
 */


class LoginFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding:FragmentLoginBinding = DataBindingUtil.inflate(inflater,
           R.layout.fragment_login, container, false)
        val repository = Repository.getInstance(requireNotNull(this.activity).application)
        val viewModelFactory = LogInViewModelFactory(repository)
        val viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(LogInViewModel::class.java)
        binding.viewModel = viewModel

        binding.lifecycleOwner = this

        viewModel.isEmailValid.observe(this, Observer { correct ->
            if(correct){
                binding.emailLayout.isErrorEnabled = false
            }else{
                binding.emailLayout.error = "Некоректный адресс электронной почты"
            }
        })

        viewModel.isPasswordValid.observe(this, Observer { correct ->
            if(correct){
                binding.passwordLayout.isErrorEnabled = false
            } else {
                binding.passwordLayout.error = "Пароль должен быть не меньше 8 символов"
            }
        })

        viewModel.moveToRegistrationFragmentEvent.observe(this, Observer {event ->
            if(event){
                this.findNavController()
                    .navigate(LoginFragmentDirections.actionLoginFragment2ToLogUpFragment2())
                viewModel.resetEvent()
            }
        })

        viewModel.errorCode.observe(this, Observer {code ->
            var stringId:String? = ""
            when(code){
                0 -> stringId = null
                1 -> stringId = "Пользователя с таким адресом электронной почты не существует"
                2 -> stringId = "Некоректные данные"
            }
            if(stringId != null){
                val mySnackbar = Snackbar.make(view!!, stringId, Snackbar.LENGTH_LONG)
                mySnackbar.show()
            } else {
                try{
                    this.findNavController()
                        .navigate(LoginFragmentDirections.actionLoginFragment2ToMainActivity())
                } catch (e: Exception){
                    Log.e("WTF", "${e.message}")
                }
            }
        })
        return binding.root
    }


}
