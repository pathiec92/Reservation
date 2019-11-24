package com.blue.goeat.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.blue.goeat.R
import com.blue.goeat.extentions.*
import com.google.firebase.auth.FirebaseAuth


class Home : AppCompatActivity() {
    companion object{
        fun navigate(activity: Activity) {
            val intent = Intent(activity, Home::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            activity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        /*val user = FirebaseAuth.getInstance().currentUser
        user?:createSignInIntent()
        user?.let {*/
            //saveUserContext(it)
            refreshUi()
       // }
        //initViews()
    }

   /* override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        saveUserContext(requestCode,resultCode,data)
        refreshUi()
    }*/

    private fun refreshUi() {
        UserContext.context ?: showToast(R.string.sign_in_failed)
        UserContext.context?.let {
            initViews()
        }
    }

    private fun initViews() {
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard/*, R.id.navigation_notifications*/
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }






}
