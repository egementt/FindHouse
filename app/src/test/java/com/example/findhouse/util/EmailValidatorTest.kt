package com.example.findhouse.util

import org.junit.Assert.*
import org.junit.Before

import org.junit.Test

class EmailValidatorTest {

    private lateinit var validEmails : String
    private lateinit var invalidEmails : String
    private lateinit var validPassword: String
    private lateinit var invalidPassword: String
    private lateinit var emailValidator: EmailValidator

    @Before
    fun setUp() {
        emailValidator = EmailValidator()
        invalidEmails =   "test123outlook.com"
        validEmails ="test123@outlook.com"
        validPassword = "admin123"
        invalidPassword = "ad12"
    }

    @Test
    fun checkIsEmailValid_returnsTrue() {
        assertTrue(emailValidator.checkIsEmailValidOrNot(validEmails))
    }

    @Test
    fun checkIsEmailValid_returnsFalse() {
        assertFalse(emailValidator.checkIsEmailValidOrNot(invalidEmails))
    }

    @Test
    fun checkIsPasswordValid_returnsTrue(){
        assertTrue("Password range is between 5..16. Its valid", emailValidator.checkIsPasswordValidOrNot(validPassword))
    }

    @Test
    fun checkIsPasswordValid_returnsFalse(){
        assertFalse("Password range must between range in 5..16. Its invalid", emailValidator.checkIsPasswordValidOrNot(invalidPassword))
    }


}