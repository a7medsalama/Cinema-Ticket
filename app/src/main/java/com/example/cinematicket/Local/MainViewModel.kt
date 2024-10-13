package com.example.cinematicket.Local

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cinematicket.Model.CartItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private var repoImp: CartRepoImp

    private var cartMutableLiveData = MutableLiveData<List<CartItem>>()
    val cartLiveData: LiveData<List<CartItem>> get() = cartMutableLiveData

    init {
        val db = CartDatabase.getDatabase(application)
        repoImp = CartRepoImp(db)
    }

    // Fetch all items from the cart and post the value to cartLiveData
    fun getAllItems() = viewModelScope.launch(Dispatchers.IO) {
        try {
            cartMutableLiveData.postValue(repoImp.getAllItems())
        } catch (e: Exception) {
            // Handle the exception, e.g., log it or notify UI
        }
    }

    // Insert a new item into the cart
    fun insertItem(cartItem: CartItem) = viewModelScope.launch(Dispatchers.IO) {
        try {
            repoImp.insertItem(cartItem)
        } catch (e: Exception) {
            // Handle the exception
        }
    }

    // Delete an item from the cart
    fun deleteItem(cartItem: CartItem) = viewModelScope.launch(Dispatchers.IO) {
        try {
            repoImp.deleteItem(cartItem)
        } catch (e: Exception) {
            // Handle the exception
        }
    }
}
