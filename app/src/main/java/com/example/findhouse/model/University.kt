package com.example.findhouse.model

import com.google.android.gms.maps.model.LatLng

data class University(
    val name: String,
    val location: LatLng
){
    fun getLatitude(): Double {
        return location.latitude
    }

    fun getLongitude(): Double{
        return location.longitude
    }
}
