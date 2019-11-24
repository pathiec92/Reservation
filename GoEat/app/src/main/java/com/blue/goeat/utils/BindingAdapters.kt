package com.blue.goeat.utils

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.blue.goeat.extentions.loadImage

object BindingAdapters{
    @BindingAdapter("loadImage")
    @JvmStatic fun loadImage(imageView: ImageView, uri: Uri){
        imageView.loadImage(uri)
    }
}