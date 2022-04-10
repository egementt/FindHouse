package com.example.findhouse.service


import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.findhouse.model.Current
import com.example.findhouse.model.Owner
import com.example.findhouse.model.Student
import com.example.findhouse.util.FirebaseResponse
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


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
        password: String): MutableLiveData<FirebaseResponse> {
        registrationStatus.value = FirebaseResponse.Loading()
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                registrationStatus.value = FirebaseResponse.Success(Current.user!!).apply {
                    FirebaseFirestore.getInstance().collection("user")
                        .document(getUserID()!!).set(Current.user!!.toFirebaseDB())
                        .addOnSuccessListener {
                            Log.d("FINDHOUSE", "User added to db !")
                        }.addOnFailureListener {
                            Log.d(
                                "FINDHOUSE",
                                "User failed when adding to db !"
                            )
                        }  }
        }}.addOnFailureListener { e ->
            registrationStatus.value = FirebaseResponse.Failed(e.localizedMessage!!)
        }
        return registrationStatus
    }

    fun loginUser(email: String, password: String): MutableLiveData<FirebaseResponse> {
        loginStatus.value = FirebaseResponse.Loading()
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                FirebaseFirestore.getInstance().collection("user").document(auth.currentUser!!.uid).get().addOnSuccessListener {
                    val haveUniversity = it.contains("university")
                    if (haveUniversity){
                        Current.user = it.toObject(Student::class.java)
                    }else{
                        Current.user = it.toObject(Owner::class.java)
                    }
                    loginStatus.value = FirebaseResponse.Success(auth.currentUser!!.uid)
                }.addOnFailureListener { e ->
                    loginStatus.value = FirebaseResponse.Failed(e.message.toString())
                }
            }
        }.addOnFailureListener {
            loginStatus.value = FirebaseResponse.Failed(it.message.toString())
        }
        return loginStatus
    }


}