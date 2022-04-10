package com.example.findhouse.model

class Owner(name: String="", surname: String="", mailAddress: String="", phoneNumber: String="") : User(
    name, surname, mailAddress,
    phoneNumber
) {

    override fun toFirebaseDB(): HashMap<String, Any> {
        return      hashMapOf(
                "name" to name,
                "surname" to surname,
                "mailAddress" to mailAddress,
                "phoneNumber" to phoneNumber,
                "userType" to "owner"

            )
        }
    }
