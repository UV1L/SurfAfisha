package com.example.surfafisha.ui.main

import android.view.LayoutInflater
import android.view.View.inflate
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.surfafisha.R

class ListFilmAdapter(private val data: ArrayList<String>) : RecyclerView.Adapter<FilmViewHolder>() {

    private val items: ArrayList<String> = data

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder =
        FilmViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.film_layout, parent, false)
        )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        holder.name.text = items[position]
        holder.img.setBackgroundResource(R.drawable.film_img)
    }

    fun addElement(element: String) {
        items.add(element)
        notifyItemInserted(itemCount - 1)
    }
}