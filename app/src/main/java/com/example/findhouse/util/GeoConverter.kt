package com.example.findhouse.util

import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.GeoPoint

class GeoConverter {

    fun convertGeoPointToLatLng(geoPoint: GeoPoint): LatLng {
        val currentGeoPoint = geoPoint
        return LatLng(geoPoint.latitude, geoPoint.longitude)
    }
}