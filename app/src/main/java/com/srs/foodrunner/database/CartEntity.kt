package com.srs.foodrunner.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.srs.foodrunner.model.MenuItem
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "cart")
@Parcelize
data class CartEntity(
    @PrimaryKey
    @ColumnInfo(name="item_id") val item_id:String,
    @ColumnInfo(name="item_name") val item_name:String,
    @ColumnInfo(name="item_price") val item_price:String,
    ):Parcelable
