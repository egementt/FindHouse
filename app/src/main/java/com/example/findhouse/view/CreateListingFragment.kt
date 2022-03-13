package com.example.findhouse.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageSwitcher
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.findhouse.R
import com.example.findhouse.adapter.RecyclerViewAdapter
import com.example.findhouse.databinding.FragmentCreateListingBinding
import com.example.findhouse.model.*
import java.text.NumberFormat
import java.util.*


class CreateListingFragment : Fragment() {

    private lateinit var binding: FragmentCreateListingBinding
    private lateinit var recyclerView: RecyclerView
    private val imageArray = arrayListOf<Uri>()

    private var RESULT_CODE = 101
    override fun onResume() {
        super.onResume()
        val listingTypeAdapter =
            ArrayAdapter(requireContext(), R.layout.dropdown_item, ListingType.toArray())
        binding.etListingType.setAdapter(listingTypeAdapter)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateListingBinding.inflate(inflater, container, false)
        recyclerView = binding.rwAddPhotos
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        binding.cwAddPhoto.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Image(s)"), 1)

        }

        binding.btnCreateListing.setOnClickListener {
            if (isEntitiesValid()) {
                CurrentListing.current = HouseListing(
                    title = binding.etTitle.text.toString(),
                    description = binding.etDescription.text.toString(),
                    photos = imageArray,
                    price = binding.etPrice.text.toString(),
                    listingType = ListingType.toListingType(binding.etListingType.toString())
                )

                findNavController().navigate(R.id.action_createListingFragment_to_addHomeDetailsFragment)
            } else {
                Toast.makeText(requireContext(), "Please check your entities !", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        binding.etPrice.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                val currentText = binding.etPrice.text.toString()
                try {
                    NumberFormat.getCurrencyInstance().apply {
                        currency = Currency.getInstance("TRY")
                        this.maximumFractionDigits = 0
                        binding.etPrice.setText(this.format(currentText.toDouble()).removePrefix("TRY"))
                    }
                }catch (e: Exception){
                    println("Exception $e")
                }
            }
        }

        return binding.root
    }


    private fun isEntitiesValid(): Boolean {
        return imageArray.isNotEmpty() && !binding.etTitle.text.isNullOrEmpty() && !binding.etDescription.text.isNullOrEmpty() && !binding.etListingType.text.isNullOrEmpty() && !binding.etPrice.text.isNullOrEmpty()
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            if (data?.clipData != null) {
                val count = data.clipData!!.itemCount
                binding.cwAddPhoto.visibility = View.INVISIBLE
                binding.rwAddPhotos.visibility = View.VISIBLE
                binding.twAddPhotos.text = "$count photos selected."

                for (i in 0 until count) {
                    imageArray.add(data.clipData!!.getItemAt(i).uri)
                }
                val adapter = RecyclerViewAdapter(imageArray)
                binding.rwAddPhotos.adapter = adapter
            }
        }
    }


}