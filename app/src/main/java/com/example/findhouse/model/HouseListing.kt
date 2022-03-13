package com.example.findhouse.model

import android.net.Uri

data class HouseListing(
    val ID: Int? = 0,
    val title: String,
    val description: String,
    val photos: List<Uri>, //from firebase
    val price: String,
    val listingType: ListingType = ListingType.FOR_RENT,
    var house: House? = null,
){

}