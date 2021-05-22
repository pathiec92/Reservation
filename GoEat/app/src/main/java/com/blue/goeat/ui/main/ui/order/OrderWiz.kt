package com.blue.goeat.ui.main.ui.order

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.observe
import com.blue.goeat.R
import com.blue.goeat.data.entity.*
import com.blue.goeat.databinding.ActivityOrderWizBinding
import com.blue.goeat.di.InjectorUtils
import com.blue.goeat.extentions.showToast
import com.blue.goeat.utils.Widget
import com.blue.goeat.utils.WidgetArgs
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class OrderWiz : AppCompatActivity() {
    private lateinit var binding: ActivityOrderWizBinding
    private lateinit var collegeWidget: Widget
    private lateinit var hotelWidget: Widget
    private lateinit var dayWidget: Widget
    private lateinit var sessionWidget: Widget
    private lateinit var dishWidget: Widget
    private val order = DishOrder()

    companion object {
        fun navigate(context: Context) = Intent(context, OrderWiz::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            context.startActivity(this)
        }
    }

    private val orderViewModel: OrderViewModel by viewModels {
        InjectorUtils.provideOrderViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_wiz)
        initWidgets()

        orderViewModel.colleges.observe(this) { collegeList ->
            collegeWidget.setTileContentList(collegeList)
        }
        orderViewModel.hotels.observe(this) { hotelList ->
            hotelWidget.setTileContentList(hotelList)

        }
        val days: ArrayList<Day> = getDays()
        dayWidget.setTileContentList(days)
        orderViewModel.sessions.observe(this) { seesionList ->
            sessionWidget.setTileContentList(seesionList)

        }
        orderViewModel.dishes.observe(this) { dishesList ->
            dishWidget.setTileContentList(dishesList)

        }
        binding.btnReserve.setOnClickListener {
            orderViewModel.save(order)
            showToast(R.string.thanks_for_order)
            finish()
        }


    }

    private fun getDays(): ArrayList<Day> {
        val calendar = Calendar.getInstance()
        val days: ArrayList<Day> = ArrayList()
        val simpleDateFormat = SimpleDateFormat("EEE d MMM")

        for (i in 1..10) {
            val display = when (i) {
                1 -> "Today"
                2 -> "Tomorrow"
                else -> simpleDateFormat.format(calendar.time)
            }
            days.add(Day(calendar.timeInMillis, display))
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }
        return days
    }

    private fun initWidgets() {
        collegeWidget = Widget(
            WidgetArgs(
                "Colleges",
                this,
                binding.collegeRoot,
                getString(R.string.which_college),
                getString(R.string.click_select)
            ) {
                 (it as College).apply {
                     order.collegeId = collegeId
                     order.orderEntity.collegeName = entityInfo.name
                     println("Selected college = $it")
                     orderViewModel.selectHotelByCollegeId(collegeId)
                 }

                hotelWidget.setDefaultTitle()
                binding.hotelRoot.visibility = View.VISIBLE
                binding.dayRoot.visibility = View.GONE
                binding.sessionRoot.visibility = View.GONE
                binding.dishRoot.visibility = View.GONE
                binding.btnReserve.visibility = View.GONE
            })

        hotelWidget = Widget(
            WidgetArgs(
                "Hotels",
                this,
                binding.hotelRoot,
                getString(R.string.which_hotel),
                getString(R.string.click_select)
            ) {
                (it as Hotel).apply {
                    order.hotelId = hotelId
                    order.orderEntity.hotelName = entityInfo.name
                    println("Selected hotels = $it")
                    orderViewModel.selectSessionByHotelId(hotelId)
                }

                sessionWidget.setDefaultTitle()
                binding.dayRoot.visibility = View.VISIBLE
                binding.sessionRoot.visibility = View.GONE
                binding.dishRoot.visibility = View.GONE
                binding.btnReserve.visibility = View.GONE
            })

        dayWidget = Widget(
            WidgetArgs(
                "Days",
                this,
                binding.dayRoot,
                getString(R.string.what_day),
                getString(R.string.click_select)
            ) {
                (it as Day).apply {
                    order.day.dayDisplay= dayDisplay
                    order.day.timeStamp = timeStamp
                    println("Selected Session = $it")
                    orderViewModel.selectDishsBySessionId(order.sessionId)

                }

                dishWidget.setDefaultTitle()
                binding.sessionRoot.visibility = View.VISIBLE
                binding.dishRoot.visibility = View.GONE
                binding.btnReserve.visibility = View.GONE

            })
        sessionWidget = Widget(
            WidgetArgs(
                "Timings",
                this,
                binding.sessionRoot,
                getString(R.string.which_session),
                getString(R.string.click_select)
            ) {
                (it as Session).apply {
                    order.sessionId = sessionId
                    order.orderEntity.sessionName = display
                    println("Selected Session = $it")
                    orderViewModel.selectDishsBySessionId(sessionId)

                }

                dishWidget.setDefaultTitle()
                binding.dishRoot.visibility = View.VISIBLE
                binding.btnReserve.visibility = View.GONE

            })

        dishWidget = Widget(
            WidgetArgs(
                "Dishes",
                this,
                binding.dishRoot,
                getString(R.string.which_dish),
                getString(R.string.click_select)
            ) {
                (it as Dish).apply {
                    order.dishId = dishId
                    order.orderEntity.dishName = entityInfo.name
                    order.orderEntity.uri = entityInfo.uri?:""
                    println("Selected Dish = $it")
                }
                binding.btnReserve.visibility = View.VISIBLE
            })
    }

}
