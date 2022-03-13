package com.example.findhouse.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.findhouse.InAppActivity
import com.example.findhouse.R
import com.example.findhouse.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.fabCreateList.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_createListingFragment)
        }
        return binding.root
    }




}