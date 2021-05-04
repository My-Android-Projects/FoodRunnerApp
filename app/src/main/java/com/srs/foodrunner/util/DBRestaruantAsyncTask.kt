package com.srs.foodrunner.util

import android.content.Context
import android.os.AsyncTask
import androidx.room.Room
import com.srs.foodrunner.database.FavouriteRestaruantEntity
import com.srs.foodrunner.database.FoodRunnerDatabase

class DBRestaruantAsyncTask(val context: Context, val favouriteRestaruantEntity: FavouriteRestaruantEntity, val mode: Int) :
    AsyncTask<Void, Void, Boolean>() {
    /*
    Mode 1->Check DB if book is favorite or not
    Mode 2->Save the book into DB as favourite
    Mode 3->Remove the favourite book
     */

    val db = Room.databaseBuilder(context, FoodRunnerDatabase::class.java, "foodrunner-db").fallbackToDestructiveMigration().build()
    override fun doInBackground(vararg params: Void?): Boolean {
        when (mode) {
            1 -> {
                val restaruant: FavouriteRestaruantEntity = db.FavouriteRestaruantDao().getMenuItem(favouriteRestaruantEntity.restaruantId.toString())
                db.close()
                return restaruant != null
                //Check DB if book is favorite or not
            }
            2 -> {
                //Save the book into DB as favourite
                db.FavouriteRestaruantDao().insert(favouriteRestaruantEntity)
                db.close()
                return true
            }
            3 -> {
                //Remove the favourite book
                db.FavouriteRestaruantDao().delete(favouriteRestaruantEntity)
                db.close()
                return true
            }
        }
        return false
    }

}