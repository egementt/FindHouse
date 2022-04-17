package com.example.findhouse.model

import com.example.findhouse.R
import com.google.firebase.firestore.GeoPoint
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import junit.framework.TestCase
import org.junit.Test

class CurrentTest {

    private var validUniversity =
        University(
            "Yasar University",
            GeoPoint(38.45484117354883, 27.202320788172972),
            R.drawable.yasar_uni
        )

    @Test
    fun getUniversitiesByName_ReturnsTrue() {
        assertTrue(Current.getUniversityByName(validUniversity.name) == validUniversity)
    }

    @Test
    fun getUniversitiesByName_ReturnsFalse(){
        assertFalse(Current.getUniversityByName("University of Washington") != validUniversity)
    }

}