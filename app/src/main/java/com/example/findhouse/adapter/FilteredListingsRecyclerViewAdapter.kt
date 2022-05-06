package com.example.findhouse.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.media.Image
import android.util.Log
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
import com.example.findhouse.model.Current
import com.example.findhouse.model.HouseListing
import com.example.findhouse.util.Converter
import com.example.findhouse.util.FilterHelper
import com.example.findhouse.util.OrderHelper
import java.util.*
import kotlin.collections.ArrayList

class FilteredListingsRecyclerViewAdapter(private val filteredList: ArrayList<HouseListing>)  :
    RecyclerView.Adapter<FilteredListingsRecyclerViewAdapter.FilteredListViewHolder>() {

    private lateinit var mListener: NewListingsRecyclerViewAdapter.onItemClickListener
    private var listing = filteredList

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: NewListingsRecyclerViewAdapter.onItemClickListener) {
        mListener = listener
    }

    class FilteredListViewHolder(view:View, listener: NewListingsRecyclerViewAdapter.onItemClickListener) : RecyclerView.ViewHolder(view) {

        init {

                itemView.setOnClickListener {
                    listener.onItemClick(adapterPosition)
                }

        }

        private val coverImg = view.findViewById<ImageView>(R.id.iw_filteritem_cover)
        private val twTitle = view.findViewById<TextView>(R.id.tw_filteritem_title)
        private val twPrice = view.findViewById<TextView>(R.id.tw_filteritem_price)
        private val twRoomNumber = view.findViewById<TextView>(R.id.tw_filterRoomNumber)
        private val twSquareMeter = view.findViewById<TextView>(R.id.tw_filter_square_meter)
        private val twUniversity = view.findViewById<TextView>(R.id.tw_filteritem_university_name)
        private val progressDrawable = CircularProgressDrawable(view.context)

        fun bindItems(listing: HouseListing){

            Glide.with(itemView).load(listing.photos[0])
                .apply(RequestOptions.bitmapTransform(RoundedCorners(12)).override(100,200).centerInside())
                .placeholder(progressDrawable).into(coverImg).waitForLayout()

            twTitle.text = listing.title
            twPrice.text = listing.price.plus(" TRY")
            twRoomNumber.text = listing.house?.squareMetre.toString().plus(" m2")
            twSquareMeter.text = listing.house?.roomNumber.toString()
            twUniversity.text = listing.university.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilteredListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_filter_listing,
            parent,
            false
        )



        return FilteredListViewHolder(view, mListener)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun searchList(searchKey: String){
       listing = listing.filter { houseListing -> houseListing.title.lowercase().contains(searchKey.lowercase()) } as ArrayList<HouseListing>
        Log.d("filter_Debug", "filter started key [$searchKey], list $listing")
        if (searchKey.isEmpty()){
           clearList()
        }
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun orderListBy(orderId : Int){
        when(orderId){
            0 -> {
                clearList()
            }
            1 -> {
                listing = Converter.listToArrayList(OrderHelper.orderByLowestPrice(listing))
            }
            2 -> {
                listing = Converter.listToArrayList(OrderHelper.orderByHighestPrice(listing))
            }
            3 -> {
                listing = Converter.listToArrayList(OrderHelper.orderByNewest(listing))
            }
            4 -> {
                listing = Converter.listToArrayList(OrderHelper.orderByOldest(listing))

            }
        }
        notifyDataSetChanged()
    }

     @SuppressLint("NotifyDataSetChanged")
     fun clearList(){
        listing = filteredList
         notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filterListBy(listingType : String= "", min: String, max: String, squareMeter: Int ){
        var newListing = FilterHelper.filterByListingType(listing, listingType)
           newListing =  FilterHelper.filterByPriceRange(newListing, min, max)
            newListing = FilterHelper.filterBySquareMeter(newListing, squareMeter)
        listing = Converter.listToArrayList(newListing)
        notifyDataSetChanged()


    }

    override fun onBindViewHolder(holder: FilteredListViewHolder, position: Int) {
       holder.bindItems(listing[position])
    }

    override fun getItemCount(): Int {
        return listing.size
    }
}