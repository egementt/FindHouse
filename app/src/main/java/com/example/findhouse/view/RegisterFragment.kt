package com.example.findhouse.view

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.findhouse.R
import com.example.findhouse.databinding.FragmentRegisterBinding
import com.example.findhouse.model.User
import com.example.findhouse.service.AuthService
import com.example.findhouse.service.DatabaseService
import com.example.findhouse.util.FirebaseResponse


class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var response: MutableLiveData<FirebaseResponse>
    private var authService: AuthService = AuthService()
    private val databaseService = DatabaseService()


    override fun onResume() {
        super.onResume()
        val universities = resources.getStringArray(R.array.universities)
        val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, universities)
        binding.autoCompleteTextView.setAdapter(adapter)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)

        val bundle = arguments
        val args = bundle?.let { RegisterFragmentArgs.fromBundle(it) }
        if (args?.userType == "Student") {

        }
        if (args?.userType == "Owner") {
            binding.dropdownMenu.visibility = View.GONE
            binding.autoCompleteTextView.visibility = View.GONE
        }

        binding.button.setOnClickListener {

            authService.createUser(mail, password
            /*todo(
               User student mi yoksa owner mi tespit et ?,
               Veriler geliyor mu kontrol et,
               DB Service fonksiyona iyi bak.



             */
            ).observe(viewLifecycleOwner, Observer {
                when (it) {
                    is FirebaseResponse.Loading -> {
                        Toast.makeText(requireContext(), "Verifying!", Toast.LENGTH_SHORT).show()
                    }
                    is FirebaseResponse.Success -> {
                        Toast.makeText(requireContext(), "Success!", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_registerFragment_to_loginFragment)


                    }
                    is FirebaseResponse.Failed -> {
                        Toast.makeText(
                            requireContext(),
                            "Failed: ${(response.value as FirebaseResponse.Failed).msg}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }
            })

        }

        return binding.root

    }

}