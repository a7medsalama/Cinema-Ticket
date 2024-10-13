package com.example.cinematicket.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cinematicket.Model.Cast
import com.example.cinematicket.databinding.ViewholderCastBinding

class CastListAdapter(private val castList: ArrayList<Cast>): RecyclerView.Adapter<CastListAdapter.CastViewHolder>() {

    private var context: Context? = null
    inner class CastViewHolder(val binding: ViewholderCastBinding): RecyclerView.ViewHolder(binding.root) {

        fun onBind(cast: Cast) {
            context?.let {
                Glide.with(it).load(cast.PicUrl).into(binding.actorImage)
            }
            binding.actorName.text = cast.Actor
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        context = parent.context
        val binding = ViewholderCastBinding.inflate(LayoutInflater.from(context), parent, false)
        return CastViewHolder(binding)
    }

    override fun getItemCount(): Int = castList.size

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        holder.onBind(castList[position])
    }
}