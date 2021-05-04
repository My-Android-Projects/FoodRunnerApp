package com.srs.foodrunner.util

import android.content.Context
import android.os.AsyncTask
import androidx.room.Room
import com.srs.foodrunner.database.FavouriteRestaruantEntity
import com.srs.foodrunner.database.FoodRunnerDatabase

class DBRestaurantGetAllAsyncTask(val context: Context) :
    AsyncTask<Void, Void, List<FavouriteRestaruantEntity>>() {


    val db = Room.databaseBuilder(context, FoodRunnerDatabase::class.java, "foodrunner-db").fallbackToDestructiveMigration().build()
    override fun doInBackground(vararg params: Void?): List<FavouriteRestaruantEntity> {
        val restaruantdetails=db.FavouriteRestaruantDao().getAllItems()
        db.close()
        return restaruantdetails

    }

}