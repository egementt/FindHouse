package com.example.findhouse.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.findhouse.R
import com.example.findhouse.databinding.FragmentLoginBinding
import com.example.findhouse.service.AuthService
import com.example.findhouse.util.FirebaseResponse

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val authService = AuthService()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       binding = FragmentLoginBinding.inflate(inflater,container, false)

        binding.btnLogin.setOnClickListener {
            val email = binding.etMail.text.toString()
            val password = binding.etPassword.text.toString()
            authService.loginUser(email, password).observe(viewLifecycleOwner, Observer { response->

                when(response){
                    is FirebaseResponse.Loading -> {
                        Toast.makeText(requireContext(), "Verifying", Toast.LENGTH_SHORT).show()
                    }
                    is FirebaseResponse.Success ->{
                        Toast.makeText(requireContext(), "Success!", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)

                    }
                    is FirebaseResponse.Failed ->{
                        Toast.makeText(requireContext(), response.msg, Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }

        binding.twCreateAccount.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_userTypeSelectionFragment)
        }

       return binding.root
    }


}