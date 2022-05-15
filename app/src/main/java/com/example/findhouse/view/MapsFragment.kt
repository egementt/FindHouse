package com.example.findhouse.view

import android.location.Geocoder
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.findhouse.R
import com.example.findhouse.databinding.FragmentMapsBinding
import com.example.findhouse.model.Current

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.GeoPoint
import java.io.IOException

class MapsFragment : Fragment() {

    private lateinit var  binding: FragmentMapsBinding

    private val callback = OnMapReadyCallback { googleMap ->
        var markedLocation = LatLng(38.42, 27.14)
        googleMap.addMarker(MarkerOptions().position(markedLocation).title("Default Location"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(markedLocation))
        googleMap.setOnMapLongClickListener { listener->
            try {

                googleMap.clear()
                markedLocation = listener
                val address = getAddress(markedLocation)
                Current.houseListing.house?.address   = address
                Current.houseListing.house?.location = GeoPoint(markedLocation.latitude, markedLocation.longitude)

                googleMap.moveCamera(CameraUpdateFactory.newLatLng(markedLocation))
                googleMap.addMarker(MarkerOptions().position(markedLocation).title(address))
                requireActivity().findViewById<TextInputEditText>(R.id.et_address1).setText(address)
            }catch (e: java.lang.Exception){
                throw  e
            }
            //x2 * y2 sqrt
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
        try {
            val geocoder = Geocoder(requireContext())
            val list = geocoder.getFromLocation(lat.latitude, lat.longitude,1)
            return list[0].getAddressLine(0)?:"UNKOWN ADDRESS"
        }catch (e: IOException){
            throw e
        }
    }
}

//todo calculate universities distance from current listing and find nearest one.
// set 2nd address to university.
// Nisan 14'ü Deniz hoca ile toplantı var. 12:00
