package com.blue.goeat.data.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.blue.goeat.utils.TileContent

data class Address(
    val street: String?, val state: String?, val city: String?, val postCode: Int
)

data class EntityInfo(val name: String="", val description: String="", val uri: String?)

@Entity
data class College(@PrimaryKey(autoGenerate = true) val collegeId: Int = 0, @Embedded val entityInfo: EntityInfo, @Embedded val address: Address? = null) :
    TileContent {
    override fun getTileText(): String {
        return entityInfo.name ?: ""
    }

    constructor(entityInfo: EntityInfo, address: Address? = null) : this(0, entityInfo, address)


}

@Entity
/*(
    foreignKeys = [ForeignKey(
        entity = College::class,
        parentColumns = ["collegeId"],
        childColumns = ["hotelId"],
        onDelete = ForeignKey.CASCADE
    )]
)*/
data class Hotel(
    @PrimaryKey(autoGenerate = true) val hotelId: Int = 0, val collegeId: Int,
    @Embedded val entityInfo: EntityInfo, @Embedded val address: Address? = null
) : TileContent {
    override fun getTileText(): String {
        return entityInfo.name ?: ""
    }

    constructor(collegeId: Int, entityInfo: EntityInfo, address: Address? = null) : this(
        0,
        collegeId,
        entityInfo,
        address
    )
}

@Entity
/*(
    foreignKeys = [ForeignKey(
        entity = Hotel::class,
        parentColumns = ["hotelId"],
        childColumns = ["sessionId"],
        onDelete = ForeignKey.CASCADE
    )]
)*/
data class Session(
    @PrimaryKey(autoGenerate = true) val sessionId: Int = 0, val hotelId: Int,
    val display: String
) : TileContent {
    override fun getTileText(): String {
        return display
    }

    constructor(hotelId: Int, display: String) : this(0, hotelId, display)
}

@Entity
/*(
    foreignKeys = [ForeignKey(
        entity = Session::class,
        parentColumns = ["sessionId"],
        childColumns = ["dishId"],
        onDelete = ForeignKey.CASCADE
    )]
)*/
data class Dish(@PrimaryKey(autoGenerate = true) val dishId: Int = 0, val sessionId: Int, @Embedded val entityInfo: EntityInfo) :
    TileContent {
    override fun getTileText(): String {
        return entityInfo.name ?: ""
    }

    constructor(sessionId: Int, entityInfo: EntityInfo) : this(0, sessionId, entityInfo)
}

data class OrderEntity(var collegeName:String = "",var hotelName:String="",var sessionName:String ="",var dishName:String="",var uri: String="")
@Entity
data class DishOrder(
    @PrimaryKey(autoGenerate = true) val orderId: Int = 0,
    var collegeId: Int = 0, var hotelId: Int = 0, var sessionId: Int = 0, var dishId: Int = 0,
    @Embedded val orderEntity: OrderEntity= OrderEntity(), @Embedded var day:Day = Day()
)

data class Day(var timeStamp:Long = 0,var dayDisplay: String=""): TileContent{
    override fun getTileText(): String {
        return dayDisplay
    }
}
