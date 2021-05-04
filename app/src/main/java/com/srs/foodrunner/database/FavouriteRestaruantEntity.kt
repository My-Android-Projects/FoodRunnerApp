package com.srs.foodrunner.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "favouriterestaruants")
data class FavouriteRestaruantEntity(

    @PrimaryKey
    @ColumnInfo(name="restaruantId") var restaruantId:String,
    @ColumnInfo(name="restaruantName") var restaruantName:String,
    @ColumnInfo(name="restaruantPricePerPerson")  var restaruantPricePerPerson:String,
    @ColumnInfo(name="restaruantRating") var restaruantRating:String,
    @ColumnInfo(name="restaruantImage") var restaruantImage:String

)
