package com.example.surfafisha.Adapters

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.domain.IObservable
import com.example.domain.IObserver
import com.example.data.Models.FilmNet
import com.example.domain.model.Film
import com.example.surfafisha.Parsers.DateParser
import com.example.surfafisha.R
import com.example.surfafisha.ui.main.FilmViewHolder


class ListFilmAdapter : RecyclerView.Adapter<FilmViewHolder>() {

    private var items: ArrayList<Film> = ArrayList()

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
        val film = items[position]

        Glide.with(holder.itemView)
            .asBitmap()
            .load(film.poster_path)
            .placeholder(R.mipmap.default_placeholder)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    holder.img.setImageBitmap(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    holder.img.setImageDrawable(placeholder)
                }
            })

        holder.name.text = film.title
        holder.overview.text = film.overview
        holder.date.text = DateParser.formatDate(film.release_date)

        if (film.favorite)
            holder.favorite.setImageResource(R.mipmap.heart_icon_fill)
        else
            holder.favorite.setImageResource(R.mipmap.heart_icon_not_fill)

//        holder.favorite.setOnClickListener {
//            film.favorite = !film.favorite
//            onBindViewHolder(holder, position)
//
//            sendUpdateEvent(film)
//        }
    }

    fun addElement(element: Film) {
        items.add(element)
        notifyItemInserted(itemCount - 1)
    }

    fun addAll(elements: List<Film>) {
        items.addAll(elements)
        notifyDataSetChanged()
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