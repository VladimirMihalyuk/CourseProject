package com.example.courseproject.accounting


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.courseproject.App
import com.example.courseproject.R
import com.example.courseproject.log_in.LoginFragmentDirections
import kotlinx.android.synthetic.main.fragment_accountig.view.*

/**
 * A simple [Fragment] subclass.
 */
class AccountigFragment : Fragment() {
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(App.prefs?.idClient == null)
            findNavController().navigate(AccountigFragmentDirections.actionAccountigFragmentToLogInFlowActivity())
    }




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_accountig, container, false)
        view.button2.setOnClickListener{
            findNavController().navigate(AccountigFragmentDirections.actionAccountigFragmentToLogInFlowActivity())
        }

        return view
    }


}
