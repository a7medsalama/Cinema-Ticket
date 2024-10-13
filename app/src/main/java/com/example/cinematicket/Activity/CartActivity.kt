package com.example.cinematicket.Activity

import CartAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cinematicket.Local.MainViewModel
import com.example.cinematicket.databinding.ActivityCartBinding

class CartActivity : AppCompatActivity() {

    lateinit var binding: ActivityCartBinding

    lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.filmItemRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.filmItemRv.adapter = CartAdapter(viewModel.cartLiveData)

        viewModel.getAllItems()

        binding.backBtn.setOnClickListener { finish() }


    }

}