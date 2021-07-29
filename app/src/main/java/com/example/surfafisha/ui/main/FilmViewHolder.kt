package com.example.surfafisha.ui.main

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.surfafisha.R

class FilmViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val name: TextView
    val img: ImageView

    init {
        name = view.findViewById<TextView>(R.id.film_layout_name)
        img = view.findViewById<ImageView>(R.id.film_img)
    }
}