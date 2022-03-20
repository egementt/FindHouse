package com.example.findhouse.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.findhouse.R
import com.example.findhouse.databinding.FragmentSetLocationBinding
import com.example.findhouse.model.Current
import com.example.findhouse.model.HouseListing
import com.example.findhouse.service.DatabaseService
import com.example.findhouse.service.StorageService
import com.example.findhouse.util.FirebaseResponse
import org.jetbrains.annotations.NotNull


class SetLocationFragment : Fragment() {

     lateinit var binding : FragmentSetLocationBinding
     private val dbService = DatabaseService()
    private val storageService = StorageService()

    override fun onResume() {
        super.onResume()
        val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, Current.listOfUniversityNames())
        binding.actUniversity.setAdapter(adapter)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSetLocationBinding.inflate(inflater, container, false)
        val mapFragment = MapsFragment()
        requireActivity().supportFragmentManager.beginTransaction().add(R.id.fragmentView, mapFragment ).commit()
        val dbRef = dbService.getReferance()

        binding.btnCreateListing.setOnClickListener {
            storageService.uploadPhotos(dbRef.id).observe(viewLifecycleOwner, Observer { uploadResponse ->
                Log.d("upload res", uploadResponse.toString())
                when(uploadResponse) {
                        is FirebaseResponse.Success ->{
                            storageService.getPhotosByListingID(dbRef.id).observe(viewLifecycleOwner, Observer { uriResponse ->

                                when(uriResponse){
                                    is FirebaseResponse.Success -> {
                                        Toast.makeText(requireContext(), "Uri(s) received", Toast.LENGTH_SHORT).show()
                                        dbService.addListingToDB(dbRef).observe(viewLifecycleOwner, Observer {
                                            when(it){
                                                is FirebaseResponse.Success -> {
                                                    binding.progressBar.visibility = View.INVISIBLE
                                                    Toast.makeText(requireContext(), "Listing added successfully", Toast.LENGTH_SHORT).show()
                                                    findNavController().navigate(R.id.action_setLocationFragment_to_homeFragment)
                                                    clearCurrents()
                                                }
                                                is FirebaseResponse.Loading -> {
                                                    binding.progressBar.visibility = View.VISIBLE
                                                }
                                                is FirebaseResponse.Failed -> {
                                                    Toast.makeText(requireContext(), it.msg, Toast.LENGTH_SHORT).show()

                                                }
                                            }
                                        })
                                    }
                                    is FirebaseResponse.Failed ->{
                                        Toast.makeText(requireContext(), "Failed, try again", Toast.LENGTH_SHORT).show()
                                    }
                                }

                            })

                        }
                        is FirebaseResponse.Failed ->{
                            Toast.makeText(requireContext(), uploadResponse.msg.toString(), Toast.LENGTH_SHORT).show()
                        }
                        is FirebaseResponse.Loading ->{
                            binding.progressBar.visibility = View.VISIBLE
                        }
                    }



            })
        }

        return binding.root
    }


    fun clearCurrents(){
        Current.houseListing = HouseListing()
        Current.photoList = arrayListOf()
    }

    /*
                                    dbService.addListingToDB(dbRef).observe(viewLifecycleOwner, Observer {
                                    when(it){
                                        is FirebaseResponse.Success -> {
                                            binding.progressBar.visibility = View.INVISIBLE
                                            Toast.makeText(requireContext(), "Listing added successfully", Toast.LENGTH_SHORT).show()
                                            findNavController().navigate(R.id.action_setLocationFragment_to_homeFragment)
                                            clearCurrents()
                                        }
                                        is FirebaseResponse.Loading -> {
                                            binding.progressBar.visibility = View.VISIBLE
                                        }
                                        is FirebaseResponse.Failed -> {
                                            Toast.makeText(requireContext(), it.msg, Toast.LENGTH_SHORT).show()

                                        }
                                    }
                                })



     */
}