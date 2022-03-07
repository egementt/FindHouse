package com.example.findhouse.model

 class Student(
     name: String,
     surname: String,
     mailAddress: String,
     phoneNumber: String,
     private val university: String

) : User(name, surname, mailAddress, phoneNumber){



     override fun toFirebaseDB(): HashMap<String,Any>{
         return hashMapOf(
             "name" to name,
             "surname" to surname,
             "mailAddress" to mailAddress,
             "phoneNumber" to phoneNumber,
             "university" to university,
             "userType" to "student"

         )
     }
}
