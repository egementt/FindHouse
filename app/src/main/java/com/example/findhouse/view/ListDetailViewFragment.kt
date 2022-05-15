package com.example.findhouse.view

import android.content.Intent
import android.content.res.ColorStateList
import android.net.Uri
import com.example.findhouse.model.Current
import com.example.findhouse.model.HouseListing


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.findhouse.R
import com.example.findhouse.adapter.ImageSliderRecyclerViewAdapter
import com.example.findhouse.adapter.ViewPagerAdapter
import com.example.findhouse.databinding.ListDetailFragmentBinding
import com.example.findhouse.service.DatabaseService
import com.example.findhouse.util.FirebaseResponse
import com.google.android.material.tabs.TabLayoutMediator

class ListDetailViewFragment : Fragment() {

    private var position = 0
    private lateinit var binding: ListDetailFragmentBinding
    private lateinit var houseListing: HouseListing
    private lateinit var databaseService: DatabaseService
    private  var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        position = arguments?.get("listPosition") as Int
        houseListing = Current.allListings[position]
        databaseService = DatabaseService()
        isFavorite = Current.favoriteListings.contains(houseListing)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        requireActivity().actionBar?.hide()
        binding = ListDetailFragmentBinding.inflate(inflater, container, false)
        binding.twDetailListingName.text = houseListing.title

        if (isFavorite){
            binding.topappbar.menu[0].iconTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.md_theme_light_error))
        }

        setupScreen()
        return binding.root
    }

    private fun setupScreen() {
        setupImageRecyclerView()
        setupViewPager()
        setupTabLayoutMediator()
        setupToolbar()
        setupOwnerInformation()
    }

    private fun setupToolbar() {
        binding.topappbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.topappbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.favorite -> {
                    if (!isFavorite){
                        addToFavorites(menuItem)
                    }else {
                        Toast.makeText(requireContext(), "Already added.", Toast.LENGTH_SHORT)
                    }
                    true
                }
                else -> false
            }
        }
    }

    private fun addToFavorites( menuItem: MenuItem){
        databaseService.addListingToFavorites(houseListing.listingID)
            .observe(viewLifecycleOwner, Observer { response ->
                when (response) {
                    is FirebaseResponse.Success -> {
                        Toast.makeText(
                            requireContext(),
                            "Added to favorites",
                            Toast.LENGTH_SHORT
                        ).show()
                        menuItem.iconTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.md_theme_light_error))
                    }
                    is FirebaseResponse.Failed -> {
                        Toast.makeText(
                            requireContext(),
                            "Failed when added favorites : ${response.msg}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is FirebaseResponse.Loading ->{
                        Toast.makeText(
                            requireContext(),
                            "Loading...",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            })

    }

    private fun setupViewPager() {
        binding.viewPagerListDetail.adapter =
            ViewPagerAdapter(childFragmentManager, lifecycle, position)
        binding.viewPagerListDetail.isUserInputEnabled = false
    }

    private fun setupImageRecyclerView() {
        binding.imageSwitcher.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.imageSwitcher.adapter =
            ImageSliderRecyclerViewAdapter(houseListing.photos as ArrayList<String>, false)
    }

    private fun setupTabLayoutMediator() {
        TabLayoutMediator(binding.tabLayout, binding.viewPagerListDetail) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Information"
                }
                1 -> {
                    tab.text = "Map"
                }
            }
        }.attach()
    }

    private fun setupOwnerInformation(){

        binding.twOwnerName.text = "Egemen Tokgöz"
        binding.btnSendMailOwner.setOnClickListener {
            val ownerMailAddress = "egementokgoz35@gmail.com"
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:$ownerMailAddress")
            }
            startActivity(intent)
        }
        binding.btnCallOwner.setOnClickListener {
            val ownerPhoneNumber = "05426796389"
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$ownerPhoneNumber"))
            startActivity(intent)
        }

    }

}