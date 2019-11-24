package com.blue.goeat.data.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

data class Address(
    val street: String?, val state: String?, val city: String?, val postCode: Int
)

data class EntityInfo(val name: String?, val description: String?, val uri: String?)
@Entity
data class College(@PrimaryKey val collegeId: Int, @Embedded val entityInfo: EntityInfo, @Embedded val address: Address? = null)

@Entity(
    foreignKeys = [ForeignKey(
        entity = College::class,
        parentColumns = ["collegeId"],
        childColumns = ["hotelId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Hotel(
    @PrimaryKey val hotelId: Int, val zoneId: Int,
    @Embedded val entityInfo: EntityInfo, @Embedded val address: Address? =null
)

@Entity(
    foreignKeys = [ForeignKey(
        entity = Hotel::class,
        parentColumns = ["hotelId"],
        childColumns = ["sessionId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Session(@PrimaryKey val sessionId: Int, val hotelId: Int,  val display:String)

@Entity(
    foreignKeys = [ForeignKey(
        entity = Session::class,
        parentColumns = ["sessionId"],
        childColumns = ["dishId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Dish(@PrimaryKey val dishId: Int, val timeId: Int,@Embedded val entityInfo: EntityInfo)