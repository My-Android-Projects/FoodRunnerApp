package com.srs.foodrunner.util

import android.content.Context
import android.os.AsyncTask
import androidx.room.Room
import com.srs.foodrunner.database.CartEntity
import com.srs.foodrunner.database.FoodRunnerDatabase

class DBCartAsyncTask(val context: Context, val cartEntity: CartEntity?, val mode: Int) :
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
                val cart: CartEntity = db.CartDao().getMenuItem(cartEntity?.item_id.toString())
                db.close()
                return cart != null
                //Check DB if book is favorite or not
            }
            2 -> {
                //Save the book into DB as favourite
                db.CartDao().insert(cartEntity!!)
                db.close()
                return true
            }
            3 -> {
                //Remove the favourite book
                db.CartDao().delete(cartEntity!!)
                db.close()
                return true
            }
            4->{
                db.CartDao().deleteAll()
                db.close()
                return true
            }


        }
        return false
    }

}