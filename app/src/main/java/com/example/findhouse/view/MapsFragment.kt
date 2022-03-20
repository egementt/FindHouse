package com.example.findhouse.view

import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.findhouse.R
import com.example.findhouse.databinding.FragmentMapsBinding
import com.example.findhouse.model.Current

import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import com.google.android.material.textfield.TextInputEditText

import java.io.IOException

class MapsFragment : Fragment(){



    private lateinit var binding: FragmentMapsBinding
    private var googleMap: GoogleMap? = null
    private var currentLocation = MutableLiveData(Current.universities[1])



    private val callback = OnMapReadyCallback { googleMap ->
        this.googleMap = googleMap

        var current = LatLng(38.42506813948854, 27.143856593627667)
        googleMap.addMarker(MarkerOptions().position(current).title("Current City"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(current))
        googleMap.setOnMapLongClickListener { listener ->

            try {
                googleMap.clear()
                current = listener




                val address = getAddress(current)
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(current))
                googleMap.addMarker(MarkerOptions().position(current).title(address))
                requireActivity().findViewById<TextInputEditText>(R.id.et_address1).setText(address)


            } catch (e: IOException) {
                Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
            }

        }
    }

    override fun onResume() {
        super.onResume()
        val actTextView = requireActivity().findViewById<AutoCompleteTextView>(R.id.act_university)
        actTextView?.setOnDismissListener {
            val selectedUniversity = Current.universities.find {
                it.name == actTextView.text.toString()
            }
            if (selectedUniversity != null) {
                currentLocation.value = selectedUniversity
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapsBinding.inflate(inflater, container, false)

        currentLocation.observe(viewLifecycleOwner, Observer { university ->
            googleMap?.moveCamera(CameraUpdateFactory.newLatLng(university.location))
             googleMap?.addMarker(
                MarkerOptions().position(university.location).title(university.name)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
            )


        })


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }


    private fun getAddress(lat: LatLng): String? {
        val geocoder = Geocoder(requireContext())
        val list = geocoder.getFromLocation(lat.latitude, lat.longitude, 1)
        return list[0].getAddressLine(0) ?: null
    }




}

//todo calculate universities distance from current listing and find nearest one.
// set 2nd address to university.
