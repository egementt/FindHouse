package com.example.findhouse.util

import junit.framework.TestCase
import org.junit.Before
import org.junit.Test

class FirebaseResponseTest  {

    private lateinit var  successResponse : FirebaseResponse.Success
    private lateinit var failedResponse  : FirebaseResponse.Failed

    @Before
    fun setup(){
        successResponse = FirebaseResponse.Success(true)
        failedResponse = FirebaseResponse.Failed("fake exception")
    }
    @Test
    fun testGetData_returnsTrue() {
        assert(successResponse.data == true)
    }
    @Test
    fun testGetData_returnsFalse(){
        assert(successResponse.data != false)
    }
    //furps
    @Test
    fun testGetMsg_returnsTrue() {
        assert(failedResponse.msg == "fake exception")
    }
    @Test
    fun testGetMsg_returnsFalse(){
        assert(failedResponse.msg != "fake failed exception")

    }
}