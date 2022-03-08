package com.example.findhouse.util

import com.google.firebase.auth.FirebaseUser


sealed class FirebaseResponse(val data: Any? = null, val msg:String? = null){
    class Success( user: Any?): FirebaseResponse(user)
    class Failed( error: String): FirebaseResponse(msg = error)
    class Loading : FirebaseResponse()
}

