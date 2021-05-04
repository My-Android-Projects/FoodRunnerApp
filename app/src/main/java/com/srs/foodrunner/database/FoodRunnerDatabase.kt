package com.srs.foodrunner.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities=[FavouriteRestaruantEntity::class,CartEntity::class],version=8)

abstract class FoodRunnerDatabase: RoomDatabase() {
    abstract fun CartDao():CartDAO
    abstract fun FavouriteRestaruantDao():FavouriteRestaruantDAO

}
