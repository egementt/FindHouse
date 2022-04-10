package com.example.findhouse.model

import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.GeoPoint

data class University(
    val name: String="",
    val location: GeoPoint = GeoPoint(0.0,0.0),
    val image : Int = 0
    )

{

    private fun getLatitude(): Double {
        return location.latitude
    }

    private fun getLongitude(): Double{
        return location.longitude
    }

    fun toFirebase(): HashMap<String,Any>{
        return hashMapOf(
            "name" to name,
            "location" to location
        )
    }
}
