package com.blue.goeat.ui.main.ui.home

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blue.goeat.data.entity.Hotel
import com.blue.goeat.extentions.UserContext
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class HomeViewModel : ViewModel() {

    var welComeMsg = "Default"

    var resourceScheme = "res"

    var uri = Uri.Builder()
        .scheme(resourceScheme)
        .path(com.blue.goeat.R.drawable.avatar.toString())
        .build()
    private val _hotels = MutableLiveData<List<Hotel>>()
    val hotels: LiveData<List<Hotel>> = _hotels
    fun updateView() {
        UserContext.context?.let {
            println("Updating views")
            welComeMsg = "Hello ${it.name}, Welcome"
            it.photoUrl?.let { photoUrl ->
                uri = photoUrl
            }

        }
        val hotelListType = object : TypeToken<ArrayList<Hotel>>() {}.type
       /* val hotels = Gson().fromJson<ArrayList<Hotel>>(hotelJson,hotelListType)
        _hotels.value = hotels*/
    }

    /*val hotelJson =" \n" +
            "  [{\n" +
            "  \"id\":\"id1\",\n" +
            "  \"name\": \"The LaLiT Golf & Spa Resort Goa\",\n" +
            "  \"url\":\"https://www.thelalit.com/wp-content/uploads/2019/08/Best-Rate-Guarantee-Goa-Desktop-Banner.jpg\",\n" +
            "  \"desc\":\"he LaLiT Golf & Spa Resort Goa is located on the Raj Baga beach in Canacona, Goa. It is surrounded by the Sahyadri mountain range, is located close to the Talpone river and offers a view of the Arabian sea.\"\n" +
            "  },\n" +
            "  {\n" +
            "  \"id\":\"id2\",\n" +
            "  \"name\": \"Resort Rio\",\n" +
            "  \"url\":\"https://media-cdn.tripadvisor.com/media/photo-w/13/05/c1/fb/resort-rio.jpg\",\n" +
            "  \"desc\":\"he LaLiT Golf & Spa Resort Goa is located on the Raj Baga beach in Canacona, Goa. It is surrounded by the Sahyadri mountain range, is located close to the Talpone river and offers a view of the Arabian sea.\"\n" +
            "  },\n" +
            "  {\n" +
            "  \"id\":\"id3\",\n" +
            "  \"name\": \"Royal Orchid Beach Resort & Spa, Goa Swimming Pool Night View Over looking\",\n" +
            "  \"url\":\"https://media-cdn.tripadvisor.com/media/photo-w/19/64/99/80/royal-orchid-beach-resort.jpg\",\n" +
            "  \"desc\":\"he LaLiT Golf & Spa Resort Goa is located on the Raj Baga beach in Canacona, Goa. It is surrounded by the Sahyadri mountain range, is located close to the Talpone river and offers a view of the Arabian sea.\"\n" +
            "  },\n" +
            "  {\n" +
            "  \"id\":\"id4\",\n" +
            "  \"name\": \"Novotel Goa Resort & Spa\",\n" +
            "  \"url\":\"https://media-cdn.tripadvisor.com/media/photo-w/13/71/74/a0/exterior-view.jpg\",\n" +
            "  \"desc\":\"he LaLiT Golf & Spa Resort Goa is located on the Raj Baga beach in Canacona, Goa. It is surrounded by the Sahyadri mountain range, is located close to the Talpone river and offers a view of the Arabian sea.\"\n" +
            "  }\n" +
            "  \n" +
            "  ]"*/


}