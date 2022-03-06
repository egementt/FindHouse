package com.example.findhouse.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.findhouse.databinding.FragmentUserTypeSelectionBinding

class UserTypeSelectionFragment : Fragment() {

    private lateinit var binding : FragmentUserTypeSelectionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserTypeSelectionBinding.inflate(inflater, container, false)

        binding.btnStudent.setOnClickListener {
            val action  = UserTypeSelectionFragmentDirections.actionUserTypeSelectionFragmentToRegisterFragment("Student")
            findNavController().navigate(action)
        }

        binding.btnOwner.setOnClickListener {
            val action  = UserTypeSelectionFragmentDirections.actionUserTypeSelectionFragmentToRegisterFragment("Owner")
            findNavController().navigate(action)
        }

        return binding.root
    }

}