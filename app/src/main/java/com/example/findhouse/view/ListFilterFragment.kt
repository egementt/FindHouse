package com.example.findhouse.view

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.findhouse.R
import com.example.findhouse.adapter.FilteredListingsRecyclerViewAdapter
import com.example.findhouse.adapter.NewListingsRecyclerViewAdapter
import com.example.findhouse.databinding.FragmentListFilterBinding
import com.example.findhouse.model.Current
import com.example.findhouse.model.HouseListing
import com.example.findhouse.service.DatabaseService
import com.example.findhouse.util.FirebaseResponse
import com.google.firebase.database.collection.LLRBNode


class ListFilterFragment : Fragment() {

    private lateinit var binding: FragmentListFilterBinding
    private  var  listings: MutableLiveData<FirebaseResponse> = MutableLiveData(FirebaseResponse.Loading())
    private lateinit var rwAdapter: FilteredListingsRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListFilterBinding.inflate(inflater, container, false)
        val universityName = arguments?.getString("universityName") ?: Current.listOfUniversityNames()[0]
        val selectedUniversity =  Current.getUniversityByName(universityName)
        listings = DatabaseService().filterAndGetAllListings(selectedUniversity)
        observeList()
        setupToolbar(universityName)


        return binding.root
    }

    private fun setupToolbar( universityName: String) {
        binding.toolbarFilterFragment.title = universityName
        binding.toolbarFilterFragment.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.toolbarFilterFragment.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.search_action -> {
                    Toast.makeText(requireContext(), "Clicked searchview", Toast.LENGTH_SHORT).show()
                    val searchView = menuItem.actionView as SearchView
                    searchView.queryHint = "Search by title"
                    searchView.setBackgroundColor(Color.WHITE)
                    searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
                        override fun onQueryTextSubmit(query: String?): Boolean {
                            return false
                        }

                        override fun onQueryTextChange(newText: String?): Boolean {
                            if (newText != null) {
                                rwAdapter.searchList(newText)
                                Log.d("filter_Debug", "search running $newText")
                            }
                            return false
                        }

                    })

                    true
                }

                else -> false
            }
        }
    }

    private fun observeList(){
        listings.observe(viewLifecycleOwner, ) { response ->
            when (response) {
                is FirebaseResponse.Success -> {
                    binding.pbListFilter.visibility = View.INVISIBLE
                    val list = response.data as ArrayList<HouseListing>
                    if (list.isNullOrEmpty()){
                        binding.layoutNoFilter.visibility = View.VISIBLE
                        binding.imageView2.visibility = View.VISIBLE
                        binding.filterEmptyTw.visibility = View.VISIBLE
                    }else{
                        Log.d("debugapp", list.toString())
                        binding.layoutNoFilter.visibility = View.INVISIBLE
                        binding.rwFilteredList.let { rw ->
                            rw.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false )
                            rwAdapter = FilteredListingsRecyclerViewAdapter(list)
                            rwAdapter.setOnItemClickListener(object: FilteredListingsRecyclerViewAdapter.onItemClickListener{
                                override fun onItemClick(position: Int) {
                                    val findOriginalIndex = Current.allListings.indexOf(list[position] )
                                    val bundle = bundleOf("listPosition" to findOriginalIndex)
                                    findNavController().navigate(R.id.action_listFilterFragment_to_listDetailViewFragment, bundle)
                                }
                            })
                            rw.adapter = rwAdapter
                        }
                    }

                }
                is FirebaseResponse.Loading -> {
                    binding.pbListFilter.visibility = View.VISIBLE
                }
                is FirebaseResponse.Failed -> {
                    binding.pbListFilter.setBackgroundColor(Color.RED)
                    Toast.makeText(requireContext(), "AN ERROR OCCURED", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }


}