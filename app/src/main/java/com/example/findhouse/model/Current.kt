package com.example.findhouse.model

import com.example.findhouse.R
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.GeoPoint

object Current {
    var houseListing : HouseListing = HouseListing()
    var user: User? = null
    var photoList: ArrayList<String> = arrayListOf()
    var allListings = emptyList<HouseListing>()

    var universities : List<University> = listOf(
        University("Yasar University", GeoPoint(38.45484117354883, 27.202320788172972), R.drawable.yasar_uni),
        University("Dokuz Eylul University", GeoPoint(38.37104669723235, 27.206114085949935), R.drawable.dokuz_eylul_uni),
        University("Ekonomi University", GeoPoint(38.387624085391444, 27.046671651310803) , R.drawable.ekonomi_uni),
        University("Demokrasi University", GeoPoint(38.394111654214704, 27.073764879489634), R.drawable.izmir_demokrasi_universitesi),
        University("Ege University", GeoPoint(38.45412707921372, 27.212790684442023), R.drawable.ege_uni)
    )

    fun listOfUniversityNames() : List<String> {
        val universityNames = emptyList<String>().toMutableList()
        universities.forEach {
            universityNames.add(it.name)
        }
        return universityNames
    }
}
