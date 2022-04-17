package com.example.findhouse.util

import android.util.Patterns

class EmailValidator  {

    private var isEmailValid = false
    private var isPasswordValid = false

    private  val EMAIL_REGEX = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"

     fun checkIsEmailValidOrNot(email: String): Boolean {
      isEmailValid = EMAIL_REGEX.toRegex().matches(email)
         return isEmailValid
    }

    fun checkIsPasswordValidOrNot(password: String): Boolean{
        if (password.length in 6..15){
           return true
        }
        return false
    }


}