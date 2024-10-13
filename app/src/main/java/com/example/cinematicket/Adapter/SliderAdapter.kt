package com.example.cinematicket.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.cinematicket.Model.SliderItems
import com.example.cinematicket.databinding.ViewholderSliderBinding

class SliderAdapter(private val sliderItem: MutableList<SliderItems>, private val viewPager2: ViewPager2)
    : RecyclerView.Adapter<SliderAdapter.SliderViewHolder>() {

        private var context: Context? = null
        private val runnable = Runnable {
            sliderItem.addAll(sliderItem)
            notifyDataSetChanged()
        }

    inner class SliderViewHolder(val binding: ViewholderSliderBinding): RecyclerView.ViewHolder(binding.root) {
        fun onBind(items: SliderItems) {
            val requestOptions = RequestOptions().transform(CenterCrop(), RoundedCorners(60))

          context?.let {
              Glide.with(it)
                  .load(items.image)
                  .apply (requestOptions)
                  .into(binding.imageSlider)
          }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
        context = parent.context
        val binding = ViewholderSliderBinding.inflate(LayoutInflater.from(context), parent, false)
        return SliderViewHolder(binding)
    }

    override fun getItemCount(): Int = sliderItem.size

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        holder.onBind(sliderItem[position])
        if (position == sliderItem.size - 2) {
            viewPager2.post(runnable)
        }
    }

}