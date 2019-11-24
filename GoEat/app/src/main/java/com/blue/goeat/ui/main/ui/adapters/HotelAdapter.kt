package com.blue.goeat.ui.main.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.blue.goeat.R
import com.blue.goeat.data.entity.Hotel
import com.blue.goeat.databinding.PastScheduledItemBinding
import com.blue.goeat.databinding.ScheduleItemBinding
import com.blue.goeat.extentions.loadImageUrl
import com.blue.goeat.extentions.showTimePicker
import java.text.SimpleDateFormat

class HotelAdapter : ListAdapter<Hotel, RecyclerView.ViewHolder>(HotelDiffCallBack()){
    enum class ViewType{
        HEADER,
        PAST_ITEMS

    }
    private val imagesArray = arrayOf(R.drawable.hotel_1, R.drawable.hotel_2, R.drawable.hotel_3, R.drawable.hotel_4)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(position == 0){
            return
        }
        val hotel = getItem(position-1)
        (holder as HotelViewHolder).bind(hotel,imagesArray[position-1])
    }

    override fun getItemCount(): Int {
        return super.getItemCount()+1
    }

    override fun getItemViewType(position: Int): Int {
        if(position == 0){
            return ViewType.HEADER.ordinal
        }
        return ViewType.PAST_ITEMS.ordinal
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(ViewType.HEADER.ordinal == viewType)
             HotelHeaderViewHolder(ScheduleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        else
             HotelViewHolder(PastScheduledItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
    class HotelHeaderViewHolder(private val binding: ScheduleItemBinding): RecyclerView.ViewHolder(binding.root){
        init {
            binding.root.setOnClickListener {
                it.context.showTimePicker { cal ->
                    println("Session selected = ${cal.time}")
                }
            }
        }
    }

    class HotelViewHolder(private val binding: PastScheduledItemBinding): RecyclerView.ViewHolder(binding.root){
       init {
           binding.dateChip.setOnClickListener {
               binding.dateChip.context?.showTimePicker {
                   val sdf = SimpleDateFormat("E, dd MMM, hh:mm a")
                   println("time = ${sdf.format(it.time)}")
                   binding.dateChip.text = sdf.format(it.time)
               }
           }
       }

        fun bind(hotel: Hotel,@DrawableRes resId: Int){
            binding.hotelName.text = hotel.entityInfo.name
            binding.hotelDesc.text = hotel.entityInfo.description
            binding.hotelImg.loadImageUrl(hotel.entityInfo.uri ?: "")
        }
    }
}




private class HotelDiffCallBack : DiffUtil.ItemCallback<Hotel>(){
    override fun areItemsTheSame(oldItem: Hotel, newItem: Hotel): Boolean {
        return  oldItem.hotelId == newItem.hotelId
    }

    override fun areContentsTheSame(oldItem: Hotel, newItem: Hotel): Boolean {
        return oldItem == newItem
    }
}