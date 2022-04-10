package com.example.findhouse.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.findhouse.R
import com.example.findhouse.databinding.FragmentAddHomeDetailsBinding
import com.example.findhouse.model.Current
import com.example.findhouse.model.HeatingType
import com.example.findhouse.model.House
import com.example.findhouse.model.NumberOfRooms


class AddHomeDetailsFragment : Fragment() {


    private lateinit var binding: FragmentAddHomeDetailsBinding

    override fun onResume() {
        super.onResume()

        binding.etBalcony.setAdapter(
            ArrayAdapter<Boolean>(
                requireContext(),
                R.layout.dropdown_item,
                arrayOf(true, false)
            )
        )
        binding.etRoomNumber.setAdapter(
            ArrayAdapter(
                requireContext(),
                R.layout.dropdown_item,
                NumberOfRooms.toArray()
            )
        )
        binding.etHeatingType.setAdapter(
            ArrayAdapter(
                requireContext(),
                R.layout.dropdown_item,
                HeatingType.toArray()
            )
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddHomeDetailsBinding.inflate(inflater, container, false)

        binding.button2.setOnClickListener {
            if (isValid()){

               Current.houseListing.house = House(
                    roomNumber = binding.etRoomNumber.text.toString(),
                    floorNumber = binding.etFloorNumber.text.toString().toInt(),
                    squareMetre = binding.etSquaremetre.text.toString().toInt(),
                    haveBalcony = binding.etBalcony.text.toString() == "true",
                    heatingType = binding.etHeatingType.text.toString(),
                    duePrice =  binding.etDuePrice.text.toString().toDouble(),
                )

                Log.d("APP_DEBUG", "Current house: ${Current.houseListing.house}")
                findNavController().navigate(R.id.action_addHomeDetailsFragment_to_setLocationFragment)

            }else{
                Toast.makeText(requireContext(), "Please fill the fields.", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }


    private fun isValid(): Boolean {
        return (!binding.etBalcony.text.isNullOrEmpty() && !binding.etDuePrice.text.isNullOrEmpty() && !binding.etFloorNumber.text.isNullOrEmpty() && !binding.etHeatingType.text.isNullOrEmpty() && !binding.etSquaremetre.text.isNullOrEmpty()) && !binding.etBalcony.text.isNullOrEmpty()
    }


}