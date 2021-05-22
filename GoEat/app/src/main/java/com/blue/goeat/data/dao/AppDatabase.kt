package com.blue.goeat.data.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.blue.goeat.data.entity.*

@Database(entities = [College::class, Hotel::class, Session::class, Dish::class, DishOrder::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getCollegeDao(): CollegeDao

    abstract fun getHotelDao(): HotelDao

    abstract fun getSessionDao(): SessionDao

    abstract fun getDishDao(): DishDao

    abstract fun getOrderDao(): OrderDao
}