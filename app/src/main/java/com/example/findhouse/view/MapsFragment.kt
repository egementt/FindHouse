package com.example.findhouse.view

import android.location.Geocoder
import android.location.Location
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.findhouse.R
import com.example.findhouse.databinding.FragmentMapsBinding

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.textfield.TextInputEditText

class MapsFragment : Fragment() {

    private lateinit var  binding: FragmentMapsBinding

    private val callback = OnMapReadyCallback { googleMap ->

        var sydney = LatLng(38.42, 27.14)
        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        googleMap.setOnMapLongClickListener { listener->
            googleMap.clear()
            sydney = listener
            val address = getAddress(sydney)
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
            googleMap.addMarker(MarkerOptions().position(sydney).title(address))
            requireActivity().findViewById<TextInputEditText>(R.id.et_address1).setText(address)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    private fun getAddress(lat: LatLng): String {
        val geocoder = Geocoder(requireContext())
        val list = geocoder.getFromLocation(lat.latitude, lat.longitude,1)
        return list[0].getAddressLine(0)?:"UNKOWN ADDRESS"
    }
}

//todo calculate universities distance from current listing and find nearest one.
// set 2nd address to university.
