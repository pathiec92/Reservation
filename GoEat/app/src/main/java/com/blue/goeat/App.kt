package com.blue.goeat

import android.app.Application
import androidx.room.Room
import com.blue.goeat.data.dao.AppDatabase
import com.blue.goeat.data.entity.*
import com.facebook.stetho.Stetho

class App : Application() {
    val appDatabase: AppDatabase = Room.databaseBuilder(
        this,
        AppDatabase::class.java, "reserve"
    ).build()

    init {
        Stetho.initializeWithDefaults(this)
        Thread{
            if(appDatabase.getCollegeDao().getAllColleges().isEmpty())
                seedData()
            else println("Database is not empty")
        }.start()

    }

    private fun seedData() {
        println("Seeding the data")
        appDatabase.apply {
        getCollegeDao().apply {
            insert(College(1, EntityInfo("VijayaNagara Engineering College", " Vijayanagar Engineering College aims to enhance the knowledge and skill of the student in the filed of technology and engineering by grooming the students in all theoretical and practical aspects and contribute significantly towards creation of strong technical workforce that would bridge the gap between industry requirements and academic orientation.",
            "http://www.rymec.in/images/slides/build.jpg")))
            insert(College(1, EntityInfo("University Visvesvaraya College of Engineering", " University Visvesvaraya College of Engineering was established in 1917, under the name Government Engineering College, by Bharat Ratna Sir M. Visvesvaraya, and was then affiliated to Bangalore University.",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcR-EEbmZ7f5raoiel5GMR2qtryDmtOlWfoTM10gjOO6zg0Hr157")))
            insert(College(1, EntityInfo("PES University", " PES University, located in Bangalore, India is one of the country’s leading teaching and research universities.",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQckxUMQzuOffdc72i4qmmfFy2dC6JJ7xFH3z1tEE9k4gKmiiud")))
        }

       val college1 =  getCollegeDao().getCollegeByName("VijayaNagara Engineering College")
       val college2 =  getCollegeDao().getCollegeByName("University Visvesvaraya College of Engineering")
       //val college3 =  getCollegeDao().getCollegeByName("PES University")
            getHotelDao().apply {
                insert(Hotel(1,college1.collegeId, EntityInfo("VijayaNagara EAST", "",
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcR_KAjSpI1jwJD08eMTCE8FkmGAu8BQH3PqKYewUwCK98xu6bqv")))
                insert(
                    Hotel(1,college1.collegeId, EntityInfo("VijayaNagara WEST", " ",
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRCeanvI75pRZ2MrkdjFdzzhbnX7j0y8TUNBX57wNRPGS-u3R71"))
                )
                insert(Hotel(1,college1.collegeId, EntityInfo("VijayaNagara SOUTH", " ",
                    "https://s-ec.bstatic.com/xdata/images/hotel/max500/26389208.jpg?k=e27ad1d2a6ec7bdf6b712c8c8d912fbf8ee9c1d7d89cb2e7fe5987dd5b1eb36e&o=")))
                //----------------------
                insert(Hotel(1,college2.collegeId, EntityInfo("UVCE EAST", "",
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcR_KAjSpI1jwJD08eMTCE8FkmGAu8BQH3PqKYewUwCK98xu6bqv")))
                insert(
                    Hotel(1,college2.collegeId, EntityInfo("UVCE WEST", " ",
                        "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRCeanvI75pRZ2MrkdjFdzzhbnX7j0y8TUNBX57wNRPGS-u3R71"))
                )
                insert(Hotel(1,college2.collegeId, EntityInfo("UVCE SOUTH", " ",
                    "https://s-ec.bstatic.com/xdata/images/hotel/max500/26389208.jpg?k=e27ad1d2a6ec7bdf6b712c8c8d912fbf8ee9c1d7d89cb2e7fe5987dd5b1eb36e&o=")))

            }

            val hotel1 = getHotelDao().getHotelByName("VijayaNagara EAST")
            val hotel2 = getHotelDao().getHotelByName("VijayaNagara SOUTH")
            getSessionDao().apply {
                insert(
                    Session(1, hotel1.hotelId,"Breakfast")
                )
                insert(
                    Session(1, hotel1.hotelId,"Lunch")
                )
                insert(
                    Session(1, hotel1.hotelId,"Evening Snacks")
                )
                insert(
                    Session(1, hotel1.hotelId,"Dinner")
                )
                //--
                insert(
                    Session(1, hotel2.hotelId,"Morning")
                )
                insert(
                    Session(1, hotel2.hotelId,"Noon")
                )
                insert(
                    Session(1, hotel2.hotelId,"Evening")
                )
                insert(
                    Session(1, hotel2.hotelId,"Night")
                )
            }
            val eastBreakfast =  getSessionDao().getByName("Breakfast")
            val eastLunch =  getSessionDao().getByName("Lunch")
            val eastEvening =  getSessionDao().getByName("Evening Snacks")
            val eastDinner =  getSessionDao().getByName("Dinner")

            val westBreakfast =  getSessionDao().getByName("Morning")
            val westLunch =  getSessionDao().getByName("Noon")
            val westEvening =  getSessionDao().getByName("Evening")
            val westDinner =  getSessionDao().getByName("Night")


            getDishDao().apply {
                insert(
                    Dish(1,eastBreakfast.sessionId, EntityInfo("Rice Dish", "",
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRvEo-8CNSHHSPImOu4mJvqW-ZsoaYXJI7pfuaF5SGRrSPrfrWi")))
                insert(
                    Dish(1,eastBreakfast.sessionId, EntityInfo("Bella Italia", "",
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRPXLCSAgpHtcZeNmm1ewbrEhqlzEK1RK2XquZYwluTx5jixmo_")))
                insert(
                    Dish(1,eastBreakfast.sessionId, EntityInfo("Casserol", "",
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQGHBNTY_2I8dhJgerjShrtzy4Hs8JK8cFl1h_zUw_eFmuXx6ju")))

                //--lunch
                insert(
                    Dish(1,eastLunch.sessionId, EntityInfo("Chilli Chicken", "",
                        "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcS0lwoMa53lKVyB2fbEXZax9lqEQYDuGhmO1ya6ifPnLZKIe38N")))
                insert( Dish(1,eastLunch.sessionId, EntityInfo("Noodles", "",
                        "https://i.pinimg.com/originals/61/38/3e/61383e675c3872ca8ef612f8a02a6dec.jpg")))

                //--evening
                insert(
                    Dish(1,eastEvening.sessionId, EntityInfo("Rice Dish", "",
                        "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRvEo-8CNSHHSPImOu4mJvqW-ZsoaYXJI7pfuaF5SGRrSPrfrWi")))
                insert(
                    Dish(1,eastEvening.sessionId, EntityInfo("Bella Italia", "",
                        "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRPXLCSAgpHtcZeNmm1ewbrEhqlzEK1RK2XquZYwluTx5jixmo_")))
                insert(
                    Dish(1,eastEvening.sessionId, EntityInfo("Casserol", "",
                        "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQGHBNTY_2I8dhJgerjShrtzy4Hs8JK8cFl1h_zUw_eFmuXx6ju")))


                //--Dinner
                insert(
                    Dish(1,eastDinner.sessionId, EntityInfo("Chilli Chicken", "",
                        "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcS0lwoMa53lKVyB2fbEXZax9lqEQYDuGhmO1ya6ifPnLZKIe38N")))
                insert( Dish(1,eastDinner.sessionId, EntityInfo("Noodles", "",
                    "https://i.pinimg.com/originals/61/38/3e/61383e675c3872ca8ef612f8a02a6dec.jpg")))

//**********
                insert(
                    Dish(1,westBreakfast.sessionId, EntityInfo("Rice Dish", "",
                        "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRvEo-8CNSHHSPImOu4mJvqW-ZsoaYXJI7pfuaF5SGRrSPrfrWi")))
                insert(
                    Dish(1,westBreakfast.sessionId, EntityInfo("Bella Italia", "",
                        "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRPXLCSAgpHtcZeNmm1ewbrEhqlzEK1RK2XquZYwluTx5jixmo_")))
                insert(
                    Dish(1,westBreakfast.sessionId, EntityInfo("Casserol", "",
                        "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQGHBNTY_2I8dhJgerjShrtzy4Hs8JK8cFl1h_zUw_eFmuXx6ju")))

                //--lunch
                insert(
                    Dish(1,westLunch.sessionId, EntityInfo("Chilli Chicken", "",
                        "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcS0lwoMa53lKVyB2fbEXZax9lqEQYDuGhmO1ya6ifPnLZKIe38N")))
                insert( Dish(1,westLunch.sessionId, EntityInfo("Noodles", "",
                    "https://i.pinimg.com/originals/61/38/3e/61383e675c3872ca8ef612f8a02a6dec.jpg")))

                //--evening
                insert(
                    Dish(1,westEvening.sessionId, EntityInfo("Rice Dish", "",
                        "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRvEo-8CNSHHSPImOu4mJvqW-ZsoaYXJI7pfuaF5SGRrSPrfrWi")))
                insert(
                    Dish(1,westEvening.sessionId, EntityInfo("Bella Italia", "",
                        "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRPXLCSAgpHtcZeNmm1ewbrEhqlzEK1RK2XquZYwluTx5jixmo_")))
                insert(
                    Dish(1,westEvening.sessionId, EntityInfo("Casserol", "",
                        "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQGHBNTY_2I8dhJgerjShrtzy4Hs8JK8cFl1h_zUw_eFmuXx6ju")))


                //--Dinner
                insert(
                    Dish(1,westDinner.sessionId, EntityInfo("Chilli Chicken", "",
                        "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcS0lwoMa53lKVyB2fbEXZax9lqEQYDuGhmO1ya6ifPnLZKIe38N")))
                insert( Dish(1,westDinner.sessionId, EntityInfo("Noodles", "",
                    "https://i.pinimg.com/originals/61/38/3e/61383e675c3872ca8ef612f8a02a6dec.jpg")))

            }


        }

    }

}