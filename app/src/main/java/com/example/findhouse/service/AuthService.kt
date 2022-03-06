package com.example.findhouse.service


import androidx.lifecycle.MutableLiveData
import com.example.findhouse.util.FirebaseResponse
import com.google.firebase.auth.FirebaseAuth


class AuthService() : FirebaseService {

    private val  auth = FirebaseAuth.getInstance()
    private val registrationStatus : MutableLiveData<FirebaseResponse> = MutableLiveData()



      fun createUser(email:String, password:String): MutableLiveData<FirebaseResponse> {
        registrationStatus.value = FirebaseResponse.Loading()
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful){
                registrationStatus.value = FirebaseResponse.Success(auth.currentUser!!)
            }
        }.addOnFailureListener { e->
            registrationStatus.value = FirebaseResponse.Failed(e.localizedMessage!!)
        }
        return registrationStatus
    }





}