package com.example.findhouse.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.findhouse.R
import com.example.findhouse.adapter.ListDetailInformationAdapter
import com.example.findhouse.databinding.FragmentListDetailInformationBinding
import com.example.findhouse.databinding.ListDetailFragmentBinding
import com.example.findhouse.model.Current
import com.example.findhouse.model.HouseListing


class ListDetailInformationFragment : Fragment() {


    private lateinit var houseListing: HouseListing
    private lateinit var binding: FragmentListDetailInformationBinding
    private  var listPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listPosition = arguments!!.get("listPosition") as Int
        houseListing = Current.allListings[listPosition]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListDetailInformationBinding.inflate(inflater, container, false)
        binding.rwListDetail.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rwListDetail.adapter  =  ListDetailInformationAdapter(houseListing)

        return binding.root
    }


}