package com.example.cinematicket.Local

import com.example.cinematicket.Model.CartItem

interface CartRepository {
    suspend fun insertItem(cartItem: CartItem)
    suspend fun getAllItems(): List<CartItem>
    suspend fun deleteItem(cartItem: CartItem)
}