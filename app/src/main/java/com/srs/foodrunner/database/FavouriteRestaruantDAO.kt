package com.srs.foodrunner.database
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavouriteRestaruantDAO {
    @Insert
    fun insert(favouriteRestaruantEntity: FavouriteRestaruantEntity)
    @Delete
    fun delete(favouriteRestaruantEntity: FavouriteRestaruantEntity)

    @Query("SELECT * FROM favouriterestaruants")
    fun getAllItems():List<FavouriteRestaruantEntity>

    @Query("SELECT * FROM  favouriterestaruants WHERE restaruantId= :restaruantId" )
    fun getMenuItem(restaruantId:String):FavouriteRestaruantEntity
}