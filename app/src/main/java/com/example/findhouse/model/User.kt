package com.example.findhouse.model

open class User(
    val name: String,
     val surname: String,
     val mailAddress: String,
    val phoneNumber: String,
) {
    open fun toFirebaseDB(): HashMap<String,Any>{
        return hashMapOf(
            "name" to name,
            "surname" to surname,
            "mailAddress" to mailAddress,
            "phoneNumber" to phoneNumber
        )
    }
}