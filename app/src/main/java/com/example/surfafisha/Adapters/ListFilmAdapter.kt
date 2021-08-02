package com.example.surfafisha.Adapters

import android.graphics.Bitmap
import android.util.ArrayMap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.surfafisha.Models.Film
import com.example.surfafisha.POJO.FilmPOJO
import com.example.surfafisha.Parsers.DateParser
import com.example.surfafisha.R
import com.example.surfafisha.ui.main.FilmViewHolder

class ListFilmAdapter : RecyclerView.Adapter<FilmViewHolder>() {

    private var items: Map<Film, Bitmap> = ArrayMap()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder =
        FilmViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.film_layout, parent, false)
        )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        val film = items.toList()[position].first

        holder.name.text = film.title
        holder.overview.text = film.overview
        holder.date.text = DateParser.formatDate(film.release_date)
        if (film.favorite)
            holder.favorite.setImageResource(R.mipmap.heart_icon_fill)
        else
            holder.favorite.setImageResource(R.mipmap.heart_icon_not_fill)

        holder.img.setImageBitmap(items[film])
    }

    fun addElement(element: Pair<Film, Bitmap>) {
        items = items.plus(element)
        notifyItemInserted(itemCount - 1)
    }
}