package com.example.findhouse.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.findhouse.R
import com.example.findhouse.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       binding = FragmentLoginBinding.inflate(inflater,container, false)

        binding.twCreateAccount.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_userTypeSelectionFragment)
        }

       return binding.root
    }


}