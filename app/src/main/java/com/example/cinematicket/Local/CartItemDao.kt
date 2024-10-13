package com.example.cinematicket.Local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.cinematicket.Model.CartItem

@Dao
interface CartItemDao {

    @Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    suspend fun insertItem(cartItem: CartItem)

    @Query("SELECT * FROM cart_items")
    suspend fun getAllItems(): List<CartItem>

    @Delete
    suspend fun deleteItem(cartItem: CartItem)
}
