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
    private val response: MutableLiveData<FirebaseResponse> =
        MutableLiveData(FirebaseResponse.Loading())
    private val filteredResponse: MutableLiveData<FirebaseResponse> =
        MutableLiveData(FirebaseResponse.Loading())
    private val favoritesResponse: MutableLiveData<FirebaseResponse> =
        MutableLiveData(FirebaseResponse.Loading())


    fun getReferance(): DocumentReference {
        return firestore.collection("listing").document()
    }


    fun addListingToDB(docRef: DocumentReference): MutableLiveData<FirebaseResponse> {
        response.value = FirebaseResponse.Loading()
        val houseListing = Current.houseListing
        houseListing.listingID = docRef.id

        docRef.set(houseListing.toFirebase()).addOnSuccessListener {

            firestore.collection("user").document(Firebase.auth.currentUser!!.uid)
                .collection("listing").document().set(houseListing.toFirebase())
                .addOnSuccessListener {
                    response.value = FirebaseResponse.Success(houseListing)

                }.addOnFailureListener {
                response.value = FirebaseResponse.Failed(it.message ?: "error occurred")
            }
        }.addOnFailureListener {
            response.value = FirebaseResponse.Failed(it.message ?: "error occurred")
        }
        return response
    }


    fun getAllListings(): MutableLiveData<FirebaseResponse> {
        val listing: MutableList<HouseListing> = mutableListOf()
        response.value = FirebaseResponse.Loading()
        firestore.collection("listing").orderBy("createdAt", Query.Direction.DESCENDING).get()
            .addOnSuccessListener { docs ->
                for (document in docs) {
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


    fun filterAndGetAllListings(university: University): MutableLiveData<FirebaseResponse> {
        val listing: MutableList<HouseListing> = mutableListOf()
        filteredResponse.value = FirebaseResponse.Loading()
        firestore.collection("listing").whereEqualTo("university", university).get()
            .addOnSuccessListener { docs ->
                for (document in docs) {
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


    fun addListingToFavorites(listingID: String): MutableLiveData<FirebaseResponse> {
        favoritesResponse.value = FirebaseResponse.Loading()
        firestore.collection("user").document(Firebase.auth.currentUser!!.uid)
            .collection("favorites").document().set(
            hashMapOf("listingID" to listingID)
        ).addOnSuccessListener {
            favoritesResponse.value = FirebaseResponse.Success(listingID)
        }.addOnFailureListener {
            favoritesResponse.value = FirebaseResponse.Failed(it.message.toString())
        }
        return favoritesResponse
    }

    //search firebase transactions
    fun getAllFavoriteListings(): MutableLiveData<FirebaseResponse> {
        val listing: MutableList<HouseListing> = mutableListOf()
        favoritesResponse.value = FirebaseResponse.Loading()
        firestore.collection("user").document(Firebase.auth.currentUser!!.uid)
            .collection("favorites").get().addOnSuccessListener { docs ->
            for (doc in docs) {
                val id = doc.getString("listingID")
                if (id != null) {
                    firestore.collection("listing").document(id).get().addOnSuccessListener { doc ->
                        Log.d("Favorites", "${doc.id} => ")
                        val houseListing = doc.toObject(HouseListing::class.java)
                        if (houseListing != null) {
                            listing.add(houseListing)
                        }

                    }
                }
            }

        }.addOnSuccessListener {
            favoritesResponse.value = FirebaseResponse.Success(listing)
        }.addOnFailureListener {
            FirebaseResponse.Failed(it.message ?: "An error occurred")
        }

        return favoritesResponse
    }

    fun removeFromFavorites(listingID: String){
        firestore.collection("user").document(Firebase.auth.currentUser!!.uid).collection("favorites").whereEqualTo("listingID", listingID).get().addOnSuccessListener {
            val docID = it.documents[0].id
            firestore.collection("user").document(Firebase.auth.currentUser!!.uid).collection("favorites").document(docID).delete().addOnSuccessListener {
                Log.d("favorites", "$docID deleted from favorites successfully")
            }.addOnFailureListener { e->
                Log.d("favorites", "$docID cannot deleted: cause -> ${e.message}")
            }
        }.addOnFailureListener { ex ->
            Log.d("favorites", ex.message.toString() )
        }
    }
}


