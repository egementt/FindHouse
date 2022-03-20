package com.example.findhouse.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.findhouse.R
import com.example.findhouse.databinding.FragmentRegisterBinding
import com.example.findhouse.model.Current
import com.example.findhouse.model.Owner
import com.example.findhouse.model.Student
import com.example.findhouse.model.User
import com.example.findhouse.service.AuthService
import com.example.findhouse.util.FirebaseResponse
import com.google.firebase.firestore.FirebaseFirestore


class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private  var response: MutableLiveData<FirebaseResponse> = MutableLiveData()
    private  var args : RegisterFragmentArgs? = null
    private var authService: AuthService = AuthService()



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
        args = bundle?.let { RegisterFragmentArgs.fromBundle(it) }
        if (args?.userType == "Student") {
            if (!binding.dropdownMenu.isVisible){
                binding.dropdownMenu.visibility = View.VISIBLE
            }
        }
        if (args?.userType == "Owner") {
            binding.dropdownMenu.visibility = View.GONE
            binding.autoCompleteTextView.visibility = View.GONE
        }

        binding.button.setOnClickListener {
           Current.user = createUserModel()
            val password = binding.etPassword.text.toString()



            authService.createUser(createUserModel().mailAddress, password)
            .observe(viewLifecycleOwner, Observer {
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
                            "Failed ",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }
            })

        }

        return binding.root

    }


        private fun createUserModel(): User{
            val name = binding.etName.text.toString()
            val surName = binding.etSurname.text.toString()
            val mail = binding.etMail.text.toString()
            val phoneNumber = binding.etPhoneNumber.text.toString()
            val university = binding.autoCompleteTextView.text.toString()
            return if (args?.userType == "Student"){
                Student(name, surName, mail, phoneNumber, university)
            }else {
                Owner(name,surName,mail,phoneNumber)
            }
        }

}