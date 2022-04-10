package com.example.findhouse.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.findhouse.R

import com.example.findhouse.model.HouseListing
import com.google.android.material.chip.Chip


class NewListingsRecyclerViewAdapter(
    newListings: ArrayList<HouseListing>

) :
    RecyclerView.Adapter<NewListingsRecyclerViewAdapter.ItemListViewHolder>() {

    private lateinit var mListener: onItemClickListener
    private val listings = newListings

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        mListener = listener
    }

    class ItemListViewHolder(itemView: View, listener: onItemClickListener) :
        RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }

        private val coverImage = itemView.findViewById<ImageView>(R.id.iw_listing)
        private val twTitle = itemView.findViewById<TextView>(R.id.tw_title)
        private val twPrice = itemView.findViewById<TextView>(R.id.tw_price)
        private val twSquareMeter = itemView.findViewById<TextView>(R.id.tw_squaremeter)
        private val twUniversity = itemView.findViewById<TextView>(R.id.tw_university)
        private val infoChip = itemView.findViewById<Chip>(R.id.new_listing_info_chip)
        private val progressDrawable = CircularProgressDrawable(itemView.context)


        @SuppressLint("ResourceType")
        fun bindItems(houseListing: HouseListing) {
            Glide.with(itemView).load(houseListing.photos[0])
                .apply(RequestOptions.overrideOf(100, 120))
                .apply(RequestOptions.bitmapTransform(RoundedCorners(14)))
                .placeholder(progressDrawable).into(coverImage)

            infoChip.text = houseListing.listingType
            twTitle.text = houseListing.title
            twPrice.text = (houseListing.price)
            twPrice.append(" TRY")
            houseListing.house?.let {
                twSquareMeter.text = houseListing.house!!.squareMetre.toString()
            }
            twSquareMeter.append(" m2")
            twUniversity.text = houseListing.university.name
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemListViewHolder {
        return ItemListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_new_listing, parent, false),
            mListener
        )
    }

    override fun onBindViewHolder(holder: ItemListViewHolder, position: Int) {
        holder.bindItems(
            listings[position]
        )
    }

    override fun getItemCount(): Int {
        return listings.size
    }


}