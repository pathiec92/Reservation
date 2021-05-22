package com.blue.goeat.data.collections

data class User(val name: String, val email: String, val cell: String = "", val url: String = "")
data class Hotel(val name: String, val url: String = "")
data class Session(val name: String, val url: String = "")
data class Dish(val name: String, val url: String = "")