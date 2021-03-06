package com.example.findhouse.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.findhouse.R
import com.example.findhouse.adapter.FilteredListingsRecyclerViewAdapter
import com.example.findhouse.databinding.BottomSheetFilterBinding
import com.example.findhouse.model.Current
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlin.math.roundToInt


class FilterBottomSheet(val adapter: FilteredListingsRecyclerViewAdapter) : BottomSheetDialogFragment() {

    private lateinit var binding : BottomSheetFilterBinding

    companion object {
        const val TAG = "ModalBottomSheet"
    }

    override fun onResume() {
        super.onResume()
        val actAdapter = ArrayAdapter(requireContext(),R.layout.dropdown_item, Current.listOfUniversityNames())
        binding.actUniversity.setAdapter(actAdapter)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View  {

        binding = BottomSheetFilterBinding.inflate(layoutInflater, container, false)


        binding.btnApplyFilter.setOnClickListener {
            adapter.clearList()
            val selectedUniversityName = binding.actUniversity.text.toString()
            adapter.filterListBy(getListingTypeFromChip(), getMin(), getMax(), getSquareMeter(), selectedUniversityName)
        }

        binding.btnClearFilter.setOnClickListener { adapter.clearList() }


        return binding.root
    }

    private fun getCheckedChip(): Int {
        return binding.chipGroup.checkedChipId
    }

    private fun getListingTypeFromChip(): String{
        when (getCheckedChip()){
            R.id.chip_for_rent -> return "For Rent"
            R.id.chip_for_sale -> return "For Sale"
        }
        return ""
    }

    private fun getMin(): String {
        return binding.etMinFilter.text.toString()
    }

    private fun getMax(): String {
        return binding.etMaxFilter.text.toString()
    }

    private fun getSquareMeter(): Int {
        return binding.sliderM2.value.roundToInt()
    }
}