package com.example.findhouse.model

import com.google.android.gms.maps.model.LatLng

object Current {
    var houseListing : HouseListing = HouseListing()
    var user: User? = null
    var photoList: ArrayList<String> = arrayListOf()

    var universities : List<University> = listOf(
        University("Yasar University", LatLng(38.45484117354883, 27.202320788172972)),
        University("Dokuz Eylul University", LatLng(38.37104669723235, 27.206114085949935)),
        University("Ekonomi University", LatLng(38.387624085391444, 27.046671651310803)),
        University("Izmir Demokrasi University", LatLng(38.394111654214704, 27.073764879489634)),
        University("Ege University", LatLng(38.45412707921372, 27.212790684442023))
    )

    fun listOfUniversityNames() : List<String> {
        val universityNames = emptyList<String>().toMutableList()
        universities.forEach {
            universityNames.add(it.name)
        }
        return universityNames
    }
}
