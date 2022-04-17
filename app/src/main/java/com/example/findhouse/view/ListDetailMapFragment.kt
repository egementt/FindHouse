package com.example.findhouse.view

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import com.example.findhouse.R
import com.example.findhouse.databinding.FragmentListDetailMapBinding
import com.example.findhouse.model.Current
import com.example.findhouse.model.HouseListing
import com.example.findhouse.util.GeoConverter

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.MarkerOptions

class ListDetailMapFragment : Fragment() {

    private lateinit var houseListing: HouseListing
    private lateinit var binding: FragmentListDetailMapBinding




    private val callback = OnMapReadyCallback { googleMap ->
        val house = houseListing.house!!
        val houseMarker = MarkerOptions().position(GeoConverter().convertGeoPointToLatLng(house.location)).title("House")
        googleMap.addMarker(houseMarker)
        val university = houseListing.university
        val universityMarker = MarkerOptions().position(GeoConverter().convertGeoPointToLatLng(university.location)).title(university.name)
        googleMap.addMarker(universityMarker)
        googleMap.addCircle(CircleOptions().center(universityMarker.position))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(houseMarker.position, 12f))

        val parentScrollView = requireActivity().findViewById<NestedScrollView>(R.id.sw_detail)
        googleMap.setOnCameraMoveStartedListener {
            parentScrollView.requestDisallowInterceptTouchEvent(true)
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val listPosition = arguments?.getInt("listPosition") ?: 0
        houseListing = Current.allListings[listPosition]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListDetailMapBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map_detail) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}