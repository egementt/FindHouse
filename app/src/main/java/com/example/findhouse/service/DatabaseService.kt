package com.example.findhouse.service

import androidx.lifecycle.MutableLiveData
import com.example.findhouse.model.Owner
import com.example.findhouse.model.Student
import com.example.findhouse.model.User
import com.example.findhouse.util.FirebaseResponse
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class DatabaseService {

    private val firestore = FirebaseFirestore.getInstance()
    private val response : MutableLiveData<FirebaseResponse> = MutableLiveData()


    fun addUserToDB(data: User){
        response.value = FirebaseResponse.Loading()
        firestore.collection("user").document().set(data.toFirebaseDB()).addOnSuccessListener {
            response.value = FirebaseResponse.Success(data)
        }.addOnFailureListener { e->
            response.value = FirebaseResponse.Failed(e.message?: "An error occurred")
        }
    }


}