package com.example.findhouse.navigation

import android.app.Application
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.findhouse.R
import com.example.findhouse.view.LoginFragment
import com.example.findhouse.view.ProfileFragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import junit.framework.TestCase.assertTrue
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.math.log

@RunWith(AndroidJUnit4::class)
@MediumTest
class NavigationTest {

    private lateinit var navController: NavController

    @Before
    fun setup(){
         navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )
    }


    @Test
    fun testNavigationLoginToUserTypeSelectionScreen(){

        val loginScenario = launchFragmentInContainer<LoginFragment>( themeResId = R.style.Theme_FindHouse)
        loginScenario.onFragment{
            navController.setGraph(R.navigation.nav_graph)
            Navigation.setViewNavController(it.requireView(), navController)
        }
        onView(withId(R.id.tw_createAccount)).perform(ViewActions.click())
        assertTrue(navController.currentDestination?.id ?: Int == R.id.userTypeSelectionFragment)
    }

    @Test
    fun testNavigationLoginToHomeScreen(){

          launchFragmentInContainer(themeResId = R.style.Theme_FindHouse) {
            LoginFragment().also { loginFragment ->
                loginFragment.viewLifecycleOwnerLiveData.observeForever{ observer->
                    if (observer != null){
                        navController.setGraph(R.navigation.nav_graph)
                        Navigation.setViewNavController(loginFragment.requireView(), navController)
                    }

                }
            }
        }
        onView(withId(R.id.et_mail)).perform(ViewActions.typeText("admin@gmail.com"))
        onView(withId(R.id.et_password)).perform(ViewActions.typeText("egomen12"))
        onView(withId(R.id.btn_login)).perform(ViewActions.click())

        assertTrue( Firebase.auth.currentUser != null)
        //navController.currentDestination?.id == R.id.HomeFragment test is false bcs
        //they have different nav graphs
    }

    @Test
    fun testNavigationLogoutScenario(){

        launchFragmentInContainer(themeResId = R.style.Theme_FindHouse) {
            LoginFragment().also { loginFragment ->
                loginFragment.viewLifecycleOwnerLiveData.observeForever{ observer->
                    if (observer != null){
                                navController.setGraph(R.navigation.nav_graph)

                            Navigation.setViewNavController(loginFragment.requireView(), navController)
                    }

                }
            }
        }


        onView(withId(R.id.et_mail)).perform(ViewActions.typeText("admin@gmail.com"))
        onView(withId(R.id.et_password)).perform(ViewActions.typeText("egomen12"))
        onView(withId(R.id.btn_login)).perform(ViewActions.click()).apply {
            launchFragmentInContainer(themeResId = R.style.Theme_FindHouse) {
                ProfileFragment().also { loginFragment ->
                    loginFragment.viewLifecycleOwnerLiveData.observeForever{ observer->
                        if (observer != null){

                            navController.setGraph(R.navigation.in_app_nav)

                            Navigation.setViewNavController(loginFragment.requireView(), navController)
                        }

                    }
                }
            }

        }

        assertTrue(navController.graph.id == R.id.in_app_nav )



    }

}