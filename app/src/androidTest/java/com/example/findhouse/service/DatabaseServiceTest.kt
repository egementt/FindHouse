package com.example.findhouse.service

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import org.junit.Assert.*

import org.junit.Test

class DatabaseServiceTest {

    private var firestore : FirebaseFirestore = FirebaseFirestore.getInstance()



    @Test
    fun addListingToDB() {
    }

    @Test
    fun getAllListingsTest(){
        firestore.collection("listings").document().get().addOnSuccessListener {
            assertTrue(it.data.toString(), it.exists()) }
        }
    }
