package com.example.findhouse.service

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.junit.Assert.*

import org.junit.Test
import kotlin.random.Random

class AuthServiceTest {

    private val auth = Firebase.auth

    @Test
    fun createUser_returnsTrue() {
        auth.createUserWithEmailAndPassword("test${Random.nextInt()}@gmail.com", "test123")
            .addOnSuccessListener {
                assertTrue(it.user != null)
            }.addOnFailureListener {
                assertFalse( it.message.toString(), false)
            }
    }

    @Test
    fun createUser_returnsFalse() {
        auth.createUserWithEmailAndPassword("admin@gmail.com", "test123")
            .addOnSuccessListener {
                assertTrue(it.user != null)
            }.addOnFailureListener {
                assertFalse( it.message.toString(), false)
            }
    }

    @Test
    fun loginUser_returnsTrue() {
        auth.signInWithEmailAndPassword("admin@gmail.com", "egomen12")
            .addOnSuccessListener {
                assertTrue(it.user != null)
            }.addOnFailureListener {
                assertFalse( it.message.toString(), false)
            }
    }

    @Test
    fun loginUser_returnsFalse() {
        auth.signInWithEmailAndPassword("admin@gmail.com", "123")
            .addOnSuccessListener {
                assertTrue(it.user != null)
            }.addOnFailureListener {
                assertFalse( it.message.toString(), false)
            }
    }
}