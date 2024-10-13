package com.example.cinematicket.Local

import com.example.cinematicket.Model.CartItem

class CartRepoImp(val db: CartDatabase): CartRepository {
    override suspend fun insertItem(cartItem: CartItem) {
        db.cartItem().insertItem(cartItem)
    }

    override suspend fun getAllItems(): List<CartItem> {
        return db.cartItem().getAllItems()
    }

    override suspend fun deleteItem(cartItem: CartItem) {
        db.cartItem().deleteItem(cartItem)
    }
}