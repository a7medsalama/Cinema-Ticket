package com.example.cinematicket.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.cinematicket.R
import com.example.cinematicket.databinding.ItemTimeBinding

class TimeAdapter(private val timeSlots: List<String>): RecyclerView.Adapter<TimeAdapter.ViewHolder>() {

    private var selectedPosition = -1
    private var lastSelectedPosition = -1
    inner class ViewHolder(val binding: ItemTimeBinding): RecyclerView.ViewHolder(binding.root) {

        fun onBind(time: String) {
            binding.timeTxt.text = time

            if (selectedPosition == position) {
                binding.timeTxt.setBackgroundResource(R.drawable.white_bg)
                binding.timeTxt.setTextColor(ContextCompat.getColor(itemView.context, R.color.black))
            }else {
                binding.timeTxt.setBackgroundResource(R.drawable.gray_bg)
                binding.timeTxt.setTextColor(ContextCompat.getColor(itemView.context, R.color.white))
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
        val binding = ItemTimeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(timeSlots[position])
    }

    override fun getItemCount(): Int = timeSlots.size
}