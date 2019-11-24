package com.blue.goeat.data.dao

import androidx.room.*
import com.blue.goeat.data.entity.College
import com.blue.goeat.data.entity.Dish
import com.blue.goeat.data.entity.Hotel
import com.blue.goeat.data.entity.Session

interface BaseDao<T> {
    @Insert
    fun insert(entity: T)

    @Insert
    fun insertMany(entities: List<T>)

    @Delete
    fun delete(entity: T)

    @Update
    fun update(entity: T)
}



@Dao
interface CollegeDao : BaseDao<College> {
    @Query("Select * From College")
    fun getAllColleges(): List<College>
    @Query("Select * from College where name = :collegeName")
    fun getCollegeByName(collegeName: String):College
}

@Dao
interface HotelDao : BaseDao<Hotel> {
    @Query("Select * From Hotel")
    fun getAllHotels():List<Hotel>

    @Query("Select * from Hotel where name = :hotelName")
    fun getHotelByName(hotelName: String):Hotel
}

@Dao
interface SessionDao : BaseDao<Session> {
    @Query("Select * From Session")
    fun getAllSessions(): List<Session>

    @Query("Select * from Session where display = :sessionName")
    fun getByName(sessionName: String):Session
}

@Dao
interface DishDao : BaseDao<Dish> {
    @Query("Select * From Dish")
    fun getAllDishes(): List<Dish>
}


