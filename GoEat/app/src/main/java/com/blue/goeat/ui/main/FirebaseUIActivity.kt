package com.blue.goeat.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.blue.goeat.R
import com.blue.goeat.extentions.createSignInIntent
import com.blue.goeat.extentions.saveUserContext
import com.blue.goeat.extentions.showToast
import com.google.firebase.auth.FirebaseAuth

class FirebaseUIActivity : AppCompatActivity() {
    companion object {
        fun navigate(activity: Activity) {
            val intent = Intent(activity, FirebaseUIActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            activity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firebase_ui)
        Log.d("Main", "Creating signIn activity")
        val user = FirebaseAuth.getInstance().currentUser
        user ?: createSignInIntent()
        user?.let {
            saveUserContext(it)
            goHome()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        saveUserContext(requestCode, resultCode, data) {
            if (it)
                goHome()
            else
                showToast(R.string.sign_in_failed)
        }
    }

    private fun goHome() {
        Home.navigate(this)
        finish()
    }


}
