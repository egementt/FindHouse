package com.example.findhouse.model

data class HouseListing(
    val ID: Int,
    val title: String,
    val photos: ArrayList<String>, //from firebase
    val price: Double,
    val listingType: ListingType = ListingType.FOR_RENT,
    val house: House,
)