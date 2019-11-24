package com.blue.goeat.data.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.blue.goeat.data.entity.College
import com.blue.goeat.data.entity.Dish
import com.blue.goeat.data.entity.Hotel

@Database(entities = [College::class, Hotel::class, Dish::class], version = 0)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getCollegeDao(): CollegeDao

    abstract fun getHotelDao(): HotelDao

    abstract fun getSessionDao(): SessionDao

    abstract fun getDishDao(): DishDao
}