package com.example.findhouse

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.findhouse.adapter.FilteredListingsRecyclerViewAdapter
import com.example.findhouse.adapter.NewListingsRecyclerViewAdapter
import com.example.findhouse.databinding.FragmentAllListingsBinding
import com.example.findhouse.model.Current
import com.example.findhouse.model.HouseListing
import com.example.findhouse.util.OrderHelper
import com.example.findhouse.view.FilterBottomSheet
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.*
import kotlin.collections.ArrayList


class AllListingsFragment : Fragment() {

    private lateinit var binding : FragmentAllListingsBinding
    private lateinit var orderList: List<String>
    private lateinit var bottomSheet : FilterBottomSheet
    private val listData = MutableLiveData(Current.allListings)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        orderList = listOf("No Order", "Lower Price Top", "Higher Price Top", "Newest", "Older")

    }

    override fun onResume() {
        super.onResume()
        orderList = listOf("No Order", "Lower Price Top", "Higher Price Top", "Newest", "Older")
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllListingsBinding.inflate(inflater, container, false)
        val adapter = initRecyclerView(listData.value as ArrayList<HouseListing>)
        bottomSheet = FilterBottomSheet(adapter)
        binding.actOrder.setAdapter(ArrayAdapter(requireContext(), R.layout.dropdown_item, orderList ))
        binding.actOrder.setOnItemClickListener { _, _, position, _ ->
            adapter.orderListBy(position)
            Toast.makeText(requireContext(), "Click $position", Toast.LENGTH_SHORT).show()
        }

        binding.btnFilter.setOnClickListener {
            bottomSheet.show(childFragmentManager, FilterBottomSheet.TAG)
        }


        return binding.root
    }

    private fun initRecyclerView(list: ArrayList<HouseListing>) : FilteredListingsRecyclerViewAdapter{
        val recyclerViewAdapter = FilteredListingsRecyclerViewAdapter(list)
        binding.rwFilteredList.let { rw ->
            rw.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false )

            recyclerViewAdapter.setOnItemClickListener(object: NewListingsRecyclerViewAdapter.onItemClickListener{
                override fun onItemClick(position: Int) {
                    val findOriginalIndex = Current.allListings.indexOf(list[position] )
                    val bundle = bundleOf("listPosition" to findOriginalIndex)
                    findNavController().navigate(R.id.action_allListingsFragment_to_listDetailViewFragment, bundle)
                }
            })
            rw.adapter = recyclerViewAdapter
            return recyclerViewAdapter
        }
    }


}