package com.example.findhouse.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.findhouse.R
import com.example.findhouse.databinding.FragmentSetLocationBinding


class SetLocationFragment : Fragment() {

     lateinit var binding : FragmentSetLocationBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSetLocationBinding.inflate(inflater, container, false)
        val mapFragment = MapsFragment()
        requireActivity().supportFragmentManager.beginTransaction().add(R.id.fragmentView, mapFragment ).commit()

        return binding.root
    }


}