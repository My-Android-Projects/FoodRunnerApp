package com.srs.foodrunner.util

import android.content.Context
import android.os.AsyncTask
import androidx.room.Room
import com.srs.foodrunner.database.CartEntity
import com.srs.foodrunner.database.FoodRunnerDatabase

class DBCartGetAllAsyncTask(val context: Context) : AsyncTask<Void, Void, List<CartEntity>>()
{
    val db = Room.databaseBuilder(context, FoodRunnerDatabase::class.java, "foodrunner-db").fallbackToDestructiveMigration().build()
    override fun doInBackground(vararg params: Void?): List<CartEntity> {
        val tempDetails=db.CartDao().getAll()
        db.close()
        return tempDetails
    }
}