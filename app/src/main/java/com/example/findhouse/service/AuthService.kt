package com.example.findhouse.service


import androidx.lifecycle.MutableLiveData
import com.example.findhouse.util.FirebaseResponse
import com.google.firebase.auth.FirebaseAuth


class AuthService() : FirebaseService {

    private val auth = FirebaseAuth.getInstance()
    private val databaseService = DatabaseService()
    private val registrationStatus: MutableLiveData<FirebaseResponse> = MutableLiveData()
    private val loginStatus: MutableLiveData<FirebaseResponse> = MutableLiveData()

    fun getUserID(): String? {
        return if (auth.currentUser != null){
            auth.currentUser!!.uid
        }else
            null
    }

    fun createUser(
        email: String,
        password: String,
        function: (() -> Unit?)? = null
    ): MutableLiveData<FirebaseResponse> {
        registrationStatus.value = FirebaseResponse.Loading()
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                registrationStatus.value = FirebaseResponse.Success(auth.currentUser!!).also {
                    function
                }

            }
        }.addOnFailureListener { e ->
            registrationStatus.value = FirebaseResponse.Failed(e.localizedMessage!!)
        }
        return registrationStatus
    }

    fun loginUser(email: String, password: String): MutableLiveData<FirebaseResponse> {
        loginStatus.value = FirebaseResponse.Loading()
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                loginStatus.value = FirebaseResponse.Success(auth.currentUser!!)
            }
        }.addOnFailureListener {
            loginStatus.value = FirebaseResponse.Failed(it.message.toString())
        }
        return loginStatus
    }


}