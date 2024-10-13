package com.example.cinematicket.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.cinematicket.Activity.FilmDetailActivity
import com.example.cinematicket.Model.Film
import com.example.cinematicket.databinding.ViewholderFilmBinding

class FilmListAdapter(private val items: ArrayList<Film>): RecyclerView.Adapter<FilmListAdapter.ViewHolder>() {

    private var context: Context? = null
    inner class ViewHolder(val binding: ViewholderFilmBinding): RecyclerView.ViewHolder(binding.root) {
        fun onBind(film: Film) {
            binding.title.text = film.Title

            val requestOptions = RequestOptions().transform(CenterCrop(), RoundedCorners(40))

            Glide.with(context!!).load(film.Poster).apply(requestOptions).into(binding.pic)

            binding.root.setOnClickListener {
                val intent = Intent(context, FilmDetailActivity::class.java)
                intent.putExtra("object", film)
                context!!.startActivity(intent)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding = ViewholderFilmBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(items[position])
    }
}