package com.example.findhouse.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.findhouse.R
import com.example.findhouse.adapter.FilteredListingsRecyclerViewAdapter
import com.example.findhouse.adapter.NewListingsRecyclerViewAdapter
import com.example.findhouse.databinding.FragmentFavoritesBinding
import com.example.findhouse.model.Current
import com.example.findhouse.model.HouseListing
import com.example.findhouse.util.SwipeToDelete


class FavoritesFragment : Fragment() {

    private lateinit var binding: FragmentFavoritesBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)

        binding.rwFavorites.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        val adapter = FilteredListingsRecyclerViewAdapter(Current.favoriteListings as ArrayList<HouseListing>)
        adapter.setOnItemClickListener(object: NewListingsRecyclerViewAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                val findOriginalIndex = Current.allListings.indexOf(Current.favoriteListings[position] )
                val bundle = bundleOf("listPosition" to findOriginalIndex)
                findNavController().navigate(R.id.action_favoritesFragment_to_listDetailViewFragment, bundle)
            }
        })
        SwipeToDelete(adapter, binding.rwFavorites)
        binding.rwFavorites.adapter = adapter



        return binding.root
    }

}