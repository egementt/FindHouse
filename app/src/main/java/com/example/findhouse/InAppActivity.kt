package com.example.findhouse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.navigation.NavHostController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.findhouse.databinding.ActivityInAppBinding
import com.example.findhouse.model.Current

import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import java.util.*

class InAppActivity : AppCompatActivity() {


     lateinit var binding: ActivityInAppBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_in_app)



        binding = ActivityInAppBinding.inflate(layoutInflater)




        val navController =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView2) as NavHostFragment

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView!!.setupWithNavController(navController = navController.navController)

        Log.d("navCDebug", navController.navController.graph.toString())


    }

    override fun onDestroy() {
        super.onDestroy()
        Firebase.auth.signOut()
    }





}
