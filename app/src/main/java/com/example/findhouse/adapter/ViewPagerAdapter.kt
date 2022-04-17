package com.example.findhouse.adapter

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.findhouse.view.ListDetailInformationFragment
import com.example.findhouse.view.ListDetailMapFragment
import com.example.findhouse.view.MapsFragment
import com.google.android.gms.maps.MapFragment

class ViewPagerAdapter (fragmentManager: FragmentManager, lifecycle: Lifecycle, listPosition: Int) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = 2
    val bundle = bundleOf("listPosition" to listPosition)


    override fun createFragment(position: Int): Fragment {
        when(position) {
            0 -> {
                val listDetailInformationFragment = ListDetailInformationFragment()
                listDetailInformationFragment.arguments = bundle
                return listDetailInformationFragment
            }

            1 -> {
                val listDetailMapFragment = ListDetailMapFragment()
                listDetailMapFragment.arguments = bundle

                return listDetailMapFragment
            }
        }
        return ListDetailInformationFragment()
    }


}