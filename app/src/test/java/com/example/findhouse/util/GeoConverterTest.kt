package com.example.findhouse.util

import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.GeoPoint
import junit.framework.TestCase

class GeoConverterTest : TestCase() {

    private val refGeoPoint = GeoPoint(38.45484117354883, 27.202320788172972)

    fun testConvertGeoPointToLatLng() {
        assert(GeoConverter().convertGeoPointToLatLng(refGeoPoint) == LatLng(refGeoPoint.latitude, refGeoPoint.longitude))
    }
}