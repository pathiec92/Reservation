package com.blue.goeat.extentions

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.StringRes
import com.blue.goeat.utils.CircleTransform
import com.squareup.picasso.Picasso
import android.content.ContentResolver
import android.view.View
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.blue.goeat.R
import java.util.*


fun Context.showToast(@StringRes msgId:Int){
    val msg = this.getString(msgId)
    Toast.makeText(this, msg,  Toast.LENGTH_SHORT).show()
}
fun Context.showToast( msg:String){
    Toast.makeText(this, msg,  Toast.LENGTH_SHORT).show()
}

fun ImageView.loadImage(uri:Uri){
    Picasso.get()
        .load(uri)
        .transform(CircleTransform())
        .into(this)
}
fun ImageView.loadImageForUri(resId: Int) {
    val uri = this.context.getUriForDrawable(resId)
    Picasso.get()
        .load(uri)
        .into(this)
}

fun ImageView.loadImageUrl(url: String) {
    when{
        url.isNotBlank() ->  Picasso.get()
            .load(url).placeholder(R.drawable.hotel_2)
            .into(this)
        else -> println("Image Url shoudn't be empty")
    }

}
fun Context.getUriForDrawable(resourceId:Int):Uri {
    val resources = this.resources
    val uri = Uri.Builder()
        .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
        .authority(resources.getResourcePackageName(resourceId))
        .appendPath(resources.getResourceTypeName(resourceId))
        .appendPath(resources.getResourceEntryName(resourceId))
        .build()
    println("Image uri = $uri")
    return uri
}

fun Context.showTimePicker(callback:(Calendar)->Unit){
    val dialogView = View.inflate(this, R.layout.date_time_picker, null)
    val alertDialog = AlertDialog.Builder(this).create()
    val datePicker = dialogView.findViewById<View>(R.id.date_picker) as DatePicker
    val timePicker = dialogView.findViewById<View>(R.id.time_picker) as TimePicker
    dialogView.findViewById<View>(R.id.date_time_set).setOnClickListener{
        val calendar = GregorianCalendar(
            datePicker.year,
            datePicker.month,
            datePicker.dayOfMonth,
            timePicker.currentHour,
            timePicker.currentMinute
        )

        callback(calendar)
        alertDialog.dismiss()
    }
    alertDialog.setView(dialogView)
    alertDialog.show()
}