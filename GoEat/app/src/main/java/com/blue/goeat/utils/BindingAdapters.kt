package com.blue.goeat.utils

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.blue.goeat.R
import com.blue.goeat.extentions.loadImage
import java.lang.Exception

object BindingAdapters{
    @BindingAdapter("loadImage")
    @JvmStatic fun loadImage(imageView: ImageView, uri: Uri){
        try{
            imageView.loadImage(uri)
        }catch (e:Exception){
            println("Exception while loading the profile image, ex =${e.message}")
        }
    }
}