package com.example.findhouse.model


import com.google.firebase.Timestamp


data class HouseListing(
    val title: String ="",
    val description: String ="",
    var photos: MutableList<String> = mutableListOf(), //from firebase
    val price: String = "",
    val listingType: String = ListingType.toString(ListingType.FOR_SALE),
    var house: House? = null,
    var createdAt : Timestamp = Timestamp.now()
){



    fun toFirebase(): HashMap<String, Any>{
        return hashMapOf(
            "title" to title,
            "description" to description,
            "photos" to photos,
            "price" to price,
            "listingType" to listingType,
            "house" to (house?.toFirebase() ?: "null"),
            "createdAt" to createdAt
        )
    }

    override fun toString(): String {
        return "HouseListing(title='$title', description='$description', photos=$photos, price='$price', listingType='$listingType', house=$house, createdAt=$createdAt)"
    }


}