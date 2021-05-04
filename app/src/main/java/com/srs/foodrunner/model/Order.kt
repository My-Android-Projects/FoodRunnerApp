package com.srs.foodrunner.model
import android.os.Parcelable

import kotlinx.android.parcel.Parcelize

data class Order (
    var order_id:String,
    var restaurant_name:String,
    var total_cost:String,
    var order_placed_at:String,
    var food_items:ArrayList<FoodItem>
    )

data class FoodItem
    (
var food_item_id:String,
var name:String,
var cost:String
            )
