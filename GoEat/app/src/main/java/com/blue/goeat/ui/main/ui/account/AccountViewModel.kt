package com.blue.goeat.ui.main.ui.account

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blue.goeat.extentions.UserContext

class AccountViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text

    var userName = "Default"
    var userEmail = "email: $userName"
    var uri:Uri? = null

    fun updateView(){
        UserContext.context?.apply{
            name?.let{userName = it}
            email?.let{userEmail = it}
            photoUrl?.let{ uri = it}
        }
    }
}