package com.example.surfafisha.ui.main

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.surfafisha.R

class FilmViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var name: TextView
    var overview: TextView
    var img: ImageView
    var date: TextView
    var favorite: ImageView

    init {
        name = view.findViewById(R.id.film_layout_name)
        overview = view.findViewById(R.id.film_layout_overview)
        img = view.findViewById(R.id.film_img)
        date = view.findViewById(R.id.film_layout_date)
        favorite = view.findViewById(R.id.film_layout_heart_icon)

        view.setOnClickListener {
            Toast.makeText(view.context, name.text, Toast.LENGTH_LONG).show()
        }
    }
}