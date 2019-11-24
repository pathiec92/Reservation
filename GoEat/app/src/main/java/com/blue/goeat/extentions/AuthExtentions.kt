package com.blue.goeat.extentions

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.blue.goeat.R
import com.blue.goeat.data.entity.UsrContext
import com.blue.goeat.ui.main.FirebaseUIActivity
import com.blue.goeat.ui.main.Home
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

const val RC_SIGN_IN = 123
fun Activity.createSignInIntent() {
    val providers = arrayListOf(
        AuthUI.IdpConfig.EmailBuilder().build(),
        AuthUI.IdpConfig.GoogleBuilder().build())

    // [START auth_fui_theme_logo]
    startActivityForResult(
        AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .setLogo(R.drawable.chef_food) // Set logo drawable
            .setTheme(R.style.AppTheme) // Set theme
            .build(),
        RC_SIGN_IN
    )
    // [END auth_fui_theme_logo]
}

fun Activity.signOut() {
    // [START auth_fui_signout]
    AuthUI.getInstance()
        .signOut(this)
        .addOnCompleteListener {
            this.showToast(R.string.sign_out_message)
           FirebaseUIActivity.navigate(this)
            this.finish()
            Log.e("Main","SignOut success, ${it.isSuccessful}, ${it.result}")
        }
    // [END auth_fui_signout]
}

fun AppCompatActivity.saveUserContext(requestCode: Int, resultCode: Int, data: Intent?, isLoginSuccess:(Boolean)->Unit){
    if (requestCode == RC_SIGN_IN) {
        val response = IdpResponse.fromResultIntent(data)
        Log.d("Main","Got the response from server")

        if (resultCode == Activity.RESULT_OK) {
            // Successfully signed in
            Log.d("Main","signIn Success!")

            val user = FirebaseAuth.getInstance().currentUser
            user?:isLoginSuccess(false)
            user?.let{
                saveUserContext(user)
                isLoginSuccess(true)
            }
            // ...
        } else {
            Log.e("Main","SigIn faile!!! email = ${response?.email}, Error${response?.error}")
            isLoginSuccess(false)
        }
    }
}

fun saveUserContext(user: FirebaseUser) {
    Log.d("Main", "user name = ${user?.displayName}")
    Log.d("Main", "user email = ${user?.email}")
    Log.d("Main", "user cell = ${user?.phoneNumber}")
    Log.d("Main", "user uid = ${user?.uid}")
    UserContext.context = UsrContext(
        user?.displayName, user?.email,
        user?.phoneNumber, user?.photoUrl
    )
}

object UserContext{
    var context: UsrContext? =null
}

