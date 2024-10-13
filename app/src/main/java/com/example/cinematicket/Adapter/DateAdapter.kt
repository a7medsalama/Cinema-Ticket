package com.example.cinematicket.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.cinematicket.R
import com.example.cinematicket.databinding.ItemDateBinding

class DateAdapter(private val dateSlots: List<String>): RecyclerView.Adapter<DateAdapter.ViewHolder>() {

    private var selectedPosition = -1
    private var lastSelectedPosition = -1
    inner class ViewHolder(val binding: ItemDateBinding): RecyclerView.ViewHolder(binding.root) {

        fun onBind(date: String) {
            val dateParts = date.split("/")
            binding.dayTxt.text = dateParts[0]
            binding.monthTxt.text = dateParts[1]+" "+dateParts[2]

            if (selectedPosition == position) {
                binding.mainLayout.setBackgroundResource(R.drawable.white_bg)
                binding.dayTxt.setTextColor(ContextCompat.getColor(itemView.context, R.color.black))
                binding.monthTxt.setTextColor(ContextCompat.getColor(itemView.context, R.color.black))
            }else {
                binding.mainLayout.setBackgroundResource(R.drawable.gray_bg)
                binding.dayTxt.setTextColor(ContextCompat.getColor(itemView.context, R.color.white))
                binding.monthTxt.setTextColor(ContextCompat.getColor(itemView.context, R.color.white))
            }

            binding.root.setOnClickListener {
                val position = position
                if (position != RecyclerView.NO_POSITION) {
                    lastSelectedPosition = selectedPosition
                    selectedPosition = position
                    notifyItemChanged(lastSelectedPosition)
                    notifyItemChanged(selectedPosition)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(dateSlots[position])
    }

    override fun getItemCount(): Int = dateSlots.size
}