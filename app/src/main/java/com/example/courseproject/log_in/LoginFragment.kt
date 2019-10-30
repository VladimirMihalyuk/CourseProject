package com.example.courseproject.log_in


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.courseproject.firebase.FirebaseHelper
import kotlinx.android.synthetic.main.fragment_login.view.*


/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : Fragment() {

    val firebaseHelper = FirebaseHelper()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(com.example.courseproject.R.layout.fragment_login, container, false)
        view.log_in.setOnClickListener{
            Log.d("WTF", "${firebaseHelper.signIn(view.email.text.toString(), view.password.text.toString())}")
        }
        view.log_up.setOnClickListener{
            Log.d("WTF", "${firebaseHelper.signUp(view.email.text.toString(), view.password.text.toString())}")
        }
        return view
    }


}
