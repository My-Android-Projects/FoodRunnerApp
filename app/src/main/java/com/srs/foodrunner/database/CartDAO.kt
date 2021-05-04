package com.srs.foodrunner.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CartDAO {
    @Insert
    fun insert(cartEntity: CartEntity)
    @Delete
    fun delete(cartEntity: CartEntity)

    @Query("DELETE FROM cart")
    fun deleteAll()

    @Query("SELECT * FROM cart")
    fun getAll():List<CartEntity>

    @Query("SELECT * FROM  cart WHERE item_id= :itemId" )
    fun getMenuItem(itemId:String):CartEntity
}