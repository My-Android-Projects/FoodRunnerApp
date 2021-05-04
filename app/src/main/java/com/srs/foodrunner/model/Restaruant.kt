package com.srs.foodrunner.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/*
  {
                "id": "1",
                "name": "Pind Tadka",
                "rating": "4.1",
                "cost_for_one": "280",
                "image_url": "https://images.pexels.com/photos/1640777/pexels-photo-1640777.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940"
            },

 */
@Parcelize
data class Restaruant(
    var restaruantId:String,
    var restaruantName:String,
    var restaruantPricePerPerson:String,
    var restaruantRating:String,
    var restaruantImage:String
): Parcelable
