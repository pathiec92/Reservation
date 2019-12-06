package com.blue.goeat.ui.main.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.blue.goeat.data.entity.DishOrder
import com.blue.goeat.databinding.PastScheduledItemBinding
import com.blue.goeat.databinding.ScheduleItemBinding
import com.blue.goeat.extentions.loadImageUrl
import com.blue.goeat.extentions.showTimePicker
import com.blue.goeat.ui.main.ui.order.OrderWiz

class DishOrderAdapter : ListAdapter<DishOrder, RecyclerView.ViewHolder>(DishOrderDiffCallBack()){
    enum class ViewType{
        HEADER,
        PAST_ITEMS
    }
    //private val imagesArray = arrayOf(R.drawable.hotel_1, R.drawable.hotel_2, R.drawable.hotel_3, R.drawable.hotel_4)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(position == 0){
            return
        }
        val hotel = getItem(position-1)
        (holder as DishOrderViewHolder).bind(hotel)
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
             DishOrderHeaderViewHolder(ScheduleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        else
             DishOrderViewHolder(PastScheduledItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
    class DishOrderHeaderViewHolder(private val binding: ScheduleItemBinding): RecyclerView.ViewHolder(binding.root){
        init {
            binding.root.setOnClickListener {
                binding.root.context.apply {OrderWiz.navigate(this ) }
               /* it.context.showTimePicker { cal ->

                    println("Session selected = ${cal.time}")
                }*/
            }
        }
    }

    class DishOrderViewHolder(private val binding: PastScheduledItemBinding): RecyclerView.ViewHolder(binding.root){
       init {
           binding.dateChip.setOnClickListener {
              /* binding.dateChip.context?.showTimePicker {
                   val sdf = SimpleDateFormat("E, dd MMM, hh:mm a")
                   println("time = ${sdf.format(it.time)}")
                   binding.dateChip.text = sdf.format(it.time)
               }*/
           }
       }

        fun bind(hotel: DishOrder){
            binding.hotelName.text = hotel.orderEntity.hotelName
            binding.dishName.text = hotel.orderEntity.dishName
            binding.dateChip.text = hotel.orderEntity.sessionName
            binding.hotelImg.loadImageUrl(hotel.orderEntity.uri )
        }
    }
}




private class DishOrderDiffCallBack : DiffUtil.ItemCallback<DishOrder>(){
    override fun areItemsTheSame(oldItem: DishOrder, newItem: DishOrder): Boolean {
        return  oldItem.hotelId == newItem.hotelId
    }

    override fun areContentsTheSame(oldItem: DishOrder, newItem: DishOrder): Boolean {
        return oldItem == newItem
    }
}