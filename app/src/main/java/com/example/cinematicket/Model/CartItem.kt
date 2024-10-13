package com.example.cinematicket.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val filmImage: String? = null,
    val title: String? = null,
    val numberOfTickets: Int,
    val totalPrice: Double
)
