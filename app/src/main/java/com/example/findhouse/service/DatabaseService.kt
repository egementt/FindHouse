package com.example.findhouse.service


import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.findhouse.model.*
import com.example.findhouse.util.FirebaseResponse
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase


class DatabaseService() {

    private val firestore = FirebaseFirestore.getInstance()
    private val response : MutableLiveData<FirebaseResponse> = MutableLiveData(FirebaseResponse.Loading())
    private val filteredResponse : MutableLiveData<FirebaseResponse> = MutableLiveData(FirebaseResponse.Loading())
    private val favoritesResponse : MutableLiveData<FirebaseResponse> = MutableLiveData(FirebaseResponse.Loading())


    fun getReferance(): DocumentReference {
        return firestore.collection("listing").document()
    }





    fun addListingToDB( docRef: DocumentReference): MutableLiveData<FirebaseResponse>{
        response.value = FirebaseResponse.Loading()
        val houseListing = Current.houseListing
        houseListing.listingID = docRef.id

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
        firestore.collection("listing").orderBy("createdAt", Query.Direction.DESCENDING).get()
           .addOnSuccessListener { docs ->
               for (document in docs){
                   Log.d("LISTINGS", "${document.id} => ")
                   val houseListing = document.toObject(HouseListing::class.java)
                   listing.add(houseListing)
                   }
               response.value = FirebaseResponse.Success(listing)
               }.addOnFailureListener {
                   response.value = FirebaseResponse.Failed(it.cause.toString())
            }


        return response
           }


    fun filterAndGetAllListings(): MutableLiveData<FirebaseResponse> {
        val listing: MutableList<HouseListing> = mutableListOf()
        filteredResponse.value = FirebaseResponse.Loading()
        firestore.collection("listing").orderBy("price").get()
            .addOnSuccessListener { docs ->
                for (document in docs){
                    Log.d("LISTINGS", "${document.id} => ")
                    val houseListing = document.toObject(HouseListing::class.java)
                    listing.add(houseListing)
                }
                filteredResponse.value = FirebaseResponse.Success(listing)
            }.addOnFailureListener {
                filteredResponse.value = FirebaseResponse.Failed(it.cause.toString())
            }


        return filteredResponse
    }


        fun addListingToFavorites(listingID: String): MutableLiveData<FirebaseResponse>{
            favoritesResponse.value = FirebaseResponse.Loading()
            firestore.collection("user").document(Firebase.auth.currentUser!!.uid).collection("favorites").document().set(
                hashMapOf("listingID" to listingID)).addOnSuccessListener {
                    favoritesResponse.value = FirebaseResponse.Success(listingID)
            }.addOnFailureListener {
                favoritesResponse.value = FirebaseResponse.Failed(it.message.toString())
            }
            return favoritesResponse
        }

    //search firebase transactions
        fun getAllFavoriteListings(): MutableLiveData<FirebaseResponse>{
            favoritesResponse.value = FirebaseResponse.Loading()
            firestore.collection("user").document(Firebase.auth.currentUser!!.uid).collection("favorites").orderBy("listingID").get().addOnSuccessListener { ids ->
                val list = mutableListOf<String>()
                for (id in ids){
                    list.add(id.toString())
                    Log.d("FirebaseLOG", "$id added successfully")
                }
            }.addOnFailureListener {
                favoritesResponse.value = FirebaseResponse.Failed(it.message.toString())
            }
        }




    }


