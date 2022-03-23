package com.example.findhouse.adapter

import android.app.Application
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.example.findhouse.R
import com.example.findhouse.model.HouseListing
import com.example.findhouse.view.HomeFragment
import com.squareup.picasso.Picasso

class NewListingsRecyclerViewAdapter(private val listings: ArrayList<HouseListing>, val homeFragment: HomeFragment) :
    RecyclerView.Adapter<NewListingsRecyclerViewAdapter.ItemListViewHolder>() {

    class ItemListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val coverImage = itemView.findViewById<ImageView>(R.id.iw_listing)
        private val twTitle = itemView.findViewById<TextView>(R.id.tw_title)
        private val twPrice = itemView.findViewById<TextView>(R.id.tw_price)
        private val twSquareMeter = itemView.findViewById<TextView>(R.id.tw_squaremeter)
        private val twUniversity = itemView.findViewById<TextView>(R.id.tw_university)
        private val progressDrawable = CircularProgressDrawable.DEFAULT

        fun bindItems(houseListing: HouseListing, homeFragment: HomeFragment) {
            Picasso.get().load(houseListing.photos[0]).into(coverImage)
            twTitle.text = houseListing.title
            twPrice.setText((houseListing.price))
            twPrice.append(" TRY")
            twSquareMeter.setText(houseListing.listingType.toString())
            twSquareMeter.append(" m2")
            twUniversity.text = houseListing.house?.roomNumber ?: "0"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemListViewHolder {
        return ItemListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.new_listing_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemListViewHolder, position: Int) {
        holder.bindItems(
            listings[position],
            homeFragment
        )
    }

    override fun getItemCount(): Int {
        return listings.size
    }


}