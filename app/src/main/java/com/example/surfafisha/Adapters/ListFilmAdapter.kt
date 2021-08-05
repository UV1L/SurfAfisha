package com.example.surfafisha.Adapters

import android.R.attr.data
import android.graphics.Bitmap
import android.util.ArrayMap
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.surfafisha.IObservable
import com.example.surfafisha.IObserver
import com.example.surfafisha.Models.Film
import com.example.surfafisha.Parsers.DateParser
import com.example.surfafisha.R
import com.example.surfafisha.ui.main.FilmViewHolder
import java.time.Duration


class ListFilmAdapter : RecyclerView.Adapter<FilmViewHolder>(), IObservable {

    private var items: ArrayList<Pair<Film, Bitmap>> = ArrayList()
    override val observers = ArrayList<IObserver>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.film_layout, parent, false)
        view.isClickable = true

        view.setOnClickListener {
            val name = it.findViewById<TextView>(R.id.film_layout_name).text
            Toast.makeText(view.context, name, Toast.LENGTH_LONG).show()
        }

        return FilmViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        val film = items[position].first
        val bitmap = items[position].second

        holder.name.text = film.title
        holder.overview.text = film.overview
        holder.date.text = DateParser.formatDate(film.release_date)
        holder.img.setImageBitmap(bitmap)
        if (film.favorite == true)
            holder.favorite.setImageResource(R.mipmap.heart_icon_fill)
        else
            holder.favorite.setImageResource(R.mipmap.heart_icon_not_fill)

        holder.favorite.setOnClickListener {
            film.favorite = !film.favorite
            onBindViewHolder(holder, position)

            sendUpdateEvent(film)
        }
    }

    fun addElement(element: Pair<Film, Bitmap>) {
        items.add(element)
        notifyItemInserted(itemCount - 1)
    }

    fun clear() {
        val size: Int = items.size
        if (size > 0) {
            for (i in 0 until size) {
                items.removeAt(0)
            }
            notifyItemRangeRemoved(0, size)
        }
    }
}