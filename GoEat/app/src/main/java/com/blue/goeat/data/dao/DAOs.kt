package com.blue.goeat.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.blue.goeat.data.entity.*

interface BaseDao<T> {
    @Insert
    suspend fun insert(entity: T)

    @Insert
    suspend fun insertMany(entities: List<T>)

    @Delete
    suspend fun delete(entity: T)

    @Update
    suspend fun update(entity: T)
}


@Dao
interface CollegeDao : BaseDao<College> {
    @Query("Select * From College")
    fun getAllColleges(): LiveData<List<College>>

    @Query("Select * from College where name = :collegeName")
    fun getCollegeByName(collegeName: String): College
}

@Dao
interface HotelDao : BaseDao<Hotel> {
    @Query("Select * From Hotel")
    fun getAllHotels(): LiveData<List<Hotel>>

    @Query("Select * From Hotel Where collegeId= :collegeId")
    fun getAllHotelsByCollegeId(collegeId: Int): LiveData<List<Hotel>>

    @Query("Select * from Hotel where name = :hotelName")
    fun getHotelByName(hotelName: String): Hotel
}

@Dao
interface SessionDao : BaseDao<Session> {
    @Query("Select * From Session")
    fun getAllSessions(): LiveData<List<Session>>

    @Query("Select * From Session Where hotelId = :hotelId")
    fun getAllSessionsByHotelId(hotelId: Int): LiveData<List<Session>>


    @Query("Select * from Session where display = :sessionName")
    fun getByName(sessionName: String): Session
}

@Dao
interface DishDao : BaseDao<Dish> {
    @Query("Select * From Dish")
    fun getAllDishes(): LiveData<List<Dish>>

    @Query("Select * From Dish where sessionId = :sessionId")
    fun getAllDishesBySessionId(sessionId: Int): LiveData<List<Dish>>
}

@Dao
interface OrderDao : BaseDao<DishOrder> {
    @Query("Select * From DishOrder")
    fun getAllOrder(): LiveData<List<DishOrder>>

    @Query("Select * From DishOrder where hotelId = :hotelId")
    fun getAllOrderByHotelId(hotelId: Int): LiveData<List<DishOrder>>
}


