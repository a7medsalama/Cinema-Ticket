package com.example.cinematicket.Activity

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cinematicket.Adapter.DateAdapter
import com.example.cinematicket.Adapter.SeatListAdapter
import com.example.cinematicket.Adapter.TimeAdapter
import com.example.cinematicket.Local.MainViewModel
import com.example.cinematicket.Model.CartItem
import com.example.cinematicket.Model.Film
import com.example.cinematicket.Model.Seat
import com.example.cinematicket.R
import com.example.cinematicket.databinding.ActivitySeatListBinding
import java.text.DecimalFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class SeatListActivity : AppCompatActivity() {

    lateinit var binding: ActivitySeatListBinding
    private lateinit var film: Film
    private var number: Int = 0
    private var price: Double = 0.0

    private lateinit var viewModel: MainViewModel

    private var itemList = arrayListOf<CartItem>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySeatListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        getExtraIntent()
        setVariable()
        initSeatList()
    }

    private fun initSeatList() {
        val gridLayoutManager = GridLayoutManager(this, 7)
        gridLayoutManager.spanSizeLookup = object: GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position % 7 == 3) 1 else 1
            }
        }
        binding.seatRv.layoutManager = gridLayoutManager

        val seatList = mutableListOf<Seat>()
        val numberSeats = 81

        for (i in 0 until numberSeats) {
            val seatName = ""
            val seatStatus =
                if (i == 2 || i == 20 || i == 33 || i == 41 || i == 72 || i == 73) Seat.SeatStatus.UNAVAILABLE else Seat.SeatStatus.AVAILABLE

            seatList.add(Seat(seatStatus, seatName))
        }

        val seatAdapter = SeatListAdapter(this, seatList, object: SeatListAdapter.SelectedSeat {
            override fun Return(selectedName: String, num: Int) {
                binding.seatNumTxt.text = "$num Seat Selected"
                val df = DecimalFormat("#.##")
                price = df.format(num * film.Price).toDouble()
                number = num
                binding.priceTxt.text = "$$price"
            }
        })

        binding.seatRv.adapter = seatAdapter
        binding.seatRv.isNestedScrollingEnabled = false

        //Time and Date Adapter ..
        binding.timeRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.timeRv.adapter = TimeAdapter(generateTimeSlots())

        binding.dateRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.dateRv.adapter = DateAdapter(generateDateSlots())

    }

    private fun setVariable() {
        binding.backBtn.setOnClickListener { finish() }
        binding.ticketBtn.setOnClickListener {
            viewModel.insertItem(CartItem(
                0,
                film.Poster,
                film.Title,
                number,
                price
            ))
        }
    }

    private fun getExtraIntent() {
        film = intent.getParcelableExtra("film")!!
    }

    private fun generateTimeSlots(): List<String> {
        val timeSlots = mutableListOf<String>()
        val formatter = DateTimeFormatter.ofPattern("hh:mm a")

        for (i in 0 until 24 step 2) {
            val time = LocalTime.of(i, 0)
            timeSlots.add(time.format(formatter))
        }
        return timeSlots
    }

    private fun generateDateSlots(): List<String> {
        val dates = mutableListOf<String>()
        val today = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("EEE/dd/MMM")

        for (i in 0 until 7) {
            dates.add(today.plusDays(i.toLong()).format(formatter))
        }
        return dates
    }

}