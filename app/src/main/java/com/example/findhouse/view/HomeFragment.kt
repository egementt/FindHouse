package com.example.findhouse.view

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.findhouse.InAppActivity
import com.example.findhouse.R
import com.example.findhouse.adapter.NewListingsRecyclerViewAdapter
import com.example.findhouse.databinding.FragmentHomeBinding
import com.example.findhouse.model.HouseListing
import com.example.findhouse.service.DatabaseService
import com.example.findhouse.util.FirebaseResponse


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    val dbService = DatabaseService()
    private val listings: MutableLiveData<FirebaseResponse> = dbService.getAllListings()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)



        listings.observe(viewLifecycleOwner, Observer { firebaseResponse ->
            when (firebaseResponse) {
                is FirebaseResponse.Loading -> {
                    Toast.makeText(requireContext(), "Loading...", Toast.LENGTH_SHORT).show()
                }
                is FirebaseResponse.Success -> {
                    val list = firebaseResponse.data as List<*>
                    Log.d("deneme", list.toString())
                    binding.newRecyclerView.let { rw ->
                        rw.layoutManager =    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                        val adapter = NewListingsRecyclerViewAdapter(list as ArrayList<HouseListing>, this)
                        rw.adapter = adapter
                    }

                }
                is FirebaseResponse.Failed -> {
                    Toast.makeText(requireContext(), "Failed", Toast.LENGTH_SHORT).show()
                }
            }
        })

        binding.fabCreateList.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_createListingFragment)
        }
        return binding.root
    }


}