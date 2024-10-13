package com.example.cinematicket.Activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.cinematicket.Adapter.FilmListAdapter
import com.example.cinematicket.Adapter.SliderAdapter
import com.example.cinematicket.Model.Film
import com.example.cinematicket.Model.SliderItems
import com.example.cinematicket.R
import com.example.cinematicket.databinding.ActivityMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private lateinit var database: FirebaseDatabase
    private val sliderHandler = Handler()
    private val sliderRunnable = Runnable {
        binding.viewPager.currentItem += 1
    }

    private lateinit var intent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance()

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        initBannar()

        initTopMovies()

        initUpcomingMovies()

        handleBottomNavBar()
    }

    private fun handleBottomNavBar() {


        binding.bottomNavBar.setItemSelected(R.id.explorer, true)

        binding.bottomNavBar.setOnItemSelectedListener { id ->
            when(id) {
                R.id.cart -> {
                    intent = Intent(this, CartActivity::class.java)
                    startActivity(intent)
                }
                else -> {}
            }
        }
    }

    private fun initTopMovies() {
        val myRef = database.getReference("Items")
        binding.progressPopular.visibility = View.VISIBLE

        myRef.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = ArrayList<Film>()

                for (childSnapshot in snapshot.children) {
                    val item = childSnapshot.getValue(Film::class.java)
                    list.add(item!!)
                }

                if (list.isNotEmpty()) {
                    binding.rvPopular.layoutManager = LinearLayoutManager(this@MainActivity,
                        LinearLayoutManager.HORIZONTAL, false)

                    binding.rvPopular.adapter = FilmListAdapter(list)
                }
                binding.progressPopular.visibility = View.GONE
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun initUpcomingMovies() {
        val myRef = database.getReference("Upcomming")
        binding.progressUpcoming.visibility = View.VISIBLE

        myRef.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = ArrayList<Film>()

                for (childSnapshot in snapshot.children) {
                    val item = childSnapshot.getValue(Film::class.java)
                    list.add(item!!)
                }

                if (list.isNotEmpty()) {
                    binding.rvUpcoming.layoutManager = LinearLayoutManager(this@MainActivity,
                        LinearLayoutManager.HORIZONTAL, false)

                    binding.rvUpcoming.adapter = FilmListAdapter(list)
                }
                binding.progressUpcoming.visibility = View.GONE
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }


    // Banners Functions
    private fun initBannar() {
        val ref = database.getReference("Banners")
        binding.progressBannar.visibility = View.VISIBLE

        ref.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var list = mutableListOf<SliderItems>()
                for (childSnapshot in snapshot.children) {
                    val item = childSnapshot.getValue(SliderItems::class.java)
                    if (item != null)
                        list.add(item)
                }

                banners(list)
                binding.progressBannar.visibility = View.GONE
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun banners(list: MutableList<SliderItems>) {
        binding.apply {
            viewPager.adapter = SliderAdapter(list, viewPager)
            viewPager.clipToPadding = false
            viewPager.clipChildren = false
            viewPager.offscreenPageLimit = 3
            viewPager.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

            val compositePageTransformer = CompositePageTransformer().apply {
                addTransformer(MarginPageTransformer(40))
                addTransformer(ViewPager2.PageTransformer { page, position ->
                    val r = 1 - Math.abs(position)
                    page.scaleY = 0.85f + r * 0.15f
                })
            }

            viewPager.setPageTransformer(compositePageTransformer)
            viewPager.currentItem = 1
            viewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    sliderHandler.removeCallbacks(sliderRunnable)
                }
            })
        }
    }

    override fun onPause() {
        super.onPause()
        sliderHandler.removeCallbacks(sliderRunnable)
    }

    override fun onResume() {
        super.onResume()
        sliderHandler.postDelayed(sliderRunnable, 2000)
    }

}