package com.example.findhouse.service


import android.net.Uri
import android.util.Log
import androidx.annotation.RestrictTo
import androidx.lifecycle.MutableLiveData
import com.example.findhouse.model.*
import com.example.findhouse.util.FirebaseResponse
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage


class DatabaseService() {

    private val firestore = FirebaseFirestore.getInstance()
    private val response : MutableLiveData<FirebaseResponse> = MutableLiveData()
    private val storageService = StorageService()

    fun getReferance(): DocumentReference {
        return firestore.collection("listing").document()
    }


    fun addUserToDB(data: User){
        response.value = FirebaseResponse.Loading()
        firestore.collection("user").document().set(data.toFirebaseDB()).addOnSuccessListener {
            response.value = FirebaseResponse.Success(data)
        }.addOnFailureListener { e->
            response.value = FirebaseResponse.Failed(e.message?: "An error occurred")
        }
    }

    fun addListingToDB( docRef: DocumentReference): MutableLiveData<FirebaseResponse>{
        response.value = FirebaseResponse.Loading()
        val houseListing = Current.houseListing

        docRef.set(houseListing.toFirebase()).addOnSuccessListener {

            firestore.collection("user").document(Firebase.auth.currentUser!!.uid).collection("listing").document().set(houseListing.toFirebase()).addOnSuccessListener {
                response.value = FirebaseResponse.Success(houseListing)

            }.addOnFailureListener {
                response.value= FirebaseResponse.Failed(it.message?: "error occurred")
            }
        }.addOnFailureListener {
            response.value = FirebaseResponse.Failed(it.message?: "error occurred")
        }
        return response
    }


    fun getAllListings(): MutableLiveData<FirebaseResponse> {
        val listing: MutableList<HouseListing> = mutableListOf()
        response.value = FirebaseResponse.Loading()
        firestore.collection("listing").orderBy("createdAt", Query.Direction.ASCENDING).get()
           .addOnSuccessListener { docs ->
               for (document in docs){
                   val time = document.data["createdAt"] as Timestamp
                   Log.d("LISTINGS", "${document.id} => ")
                   val houseListing = document.toObject(HouseListing::class.java)
                   listing.add(houseListing)
                   }
               response.value = FirebaseResponse.Success(listing)
               }


        return response
           }



    }


