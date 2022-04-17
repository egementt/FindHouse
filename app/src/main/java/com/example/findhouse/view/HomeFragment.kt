package com.example.findhouse.view

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.findhouse.InAppActivity
import com.example.findhouse.R
import com.example.findhouse.adapter.NewListingsRecyclerViewAdapter
import com.example.findhouse.adapter.UniversitiesRecyclerViewAdapter
import com.example.findhouse.databinding.FragmentHomeBinding
import com.example.findhouse.model.*
import com.example.findhouse.service.DatabaseService
import com.example.findhouse.util.FirebaseResponse
import com.google.android.material.snackbar.Snackbar
import com.example.findhouse.model.HouseListing as HouseListing


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    val dbService = DatabaseService()
    private val listings: MutableLiveData<FirebaseResponse> = dbService.getAllListings()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)



        when (Current.user) {
            is Student ->
                binding.fabCreateList.visibility = View.GONE
            is Owner -> {
                if (!binding.fabCreateList.isVisible) {
                    binding.fabCreateList.visibility = View.VISIBLE
                }
            }

        }

        loadFavorites()

        listings.observe(viewLifecycleOwner, Observer { firebaseResponse ->
            when (firebaseResponse) {
                is FirebaseResponse.Loading -> {
                    Toast.makeText(requireContext(), "Loading...", Toast.LENGTH_SHORT).show()
                }
                is FirebaseResponse.Success -> {
                    val list = firebaseResponse.data as List<*>
                    Current.allListings = list as List<HouseListing>
                    Log.d("deneme", list.toString())

                    setupUniversitiesRW()
                    setupRecentlyAddedRW(list as ArrayList<HouseListing>)
                    setupForRentRW(list)

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

    private fun loadFavorites() {
        dbService.getAllFavoriteListings().observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is FirebaseResponse.Loading -> {}
                is FirebaseResponse.Success -> {
                    Current.favoriteListings = (response.data as MutableList<HouseListing>)
                }
                is FirebaseResponse.Failed -> {
                    Snackbar.make(
                        requireContext(),
                        binding.root,
                        "Favorites Could Not Load",
                        Snackbar.LENGTH_LONG
                    ).setAction("RELOAD", View.OnClickListener {
                        loadFavorites()
                    }).show()
                }
            }

        })
    }

    private fun setupUniversitiesRW() {
        binding.rwUniversities.let { rw ->
            rw.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, true)
            val adapter = UniversitiesRecyclerViewAdapter()
            rw.adapter = adapter
            adapter.setOnItemClickListener(object :
                UniversitiesRecyclerViewAdapter.onItemClickListener {
                override fun onItemClick(position: Int) {
                    val bundle = bundleOf("universityName" to Current.universities[position].name)
                    findNavController().navigate(
                        R.id.action_homeFragment_to_listFilterFragment,
                        bundle
                    )
                }
            })

        }
    }

    private fun setupRecentlyAddedRW(arrayList: ArrayList<HouseListing>) {
        binding.newRecyclerView.let { rw ->
            rw.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            val adapter = NewListingsRecyclerViewAdapter(arrayList)
            rw.adapter = adapter
            adapter.setOnItemClickListener(object :
                NewListingsRecyclerViewAdapter.onItemClickListener {
                override fun onItemClick(position: Int) {
                    val bundle = bundleOf("listPosition" to position)
                    findNavController().navigate(
                        R.id.action_homeFragment_to_listDetailViewFragment,
                        bundle
                    )
                }
            })

        }
    }

    private fun setupForRentRW(arrayList: ArrayList<HouseListing>) {
        binding.rwForRent.let { rw ->
            rw.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            val adapter =
                NewListingsRecyclerViewAdapter(arrayList.filter { houseListing: HouseListing -> houseListing.listingType == "FOR RENT" } as ArrayList<HouseListing>)
            rw.adapter = adapter
            adapter.setOnItemClickListener(object :
                NewListingsRecyclerViewAdapter.onItemClickListener {
                override fun onItemClick(position: Int) {
                    val bundle = bundleOf("listPosition" to position)
                    findNavController().navigate(
                        R.id.action_homeFragment_to_listDetailViewFragment,
                        bundle
                    )
                }
            })

        }
    }


}