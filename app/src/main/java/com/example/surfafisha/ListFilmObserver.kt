package com.example.surfafisha

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.surfafisha.Adapters.ListFilmAdapter
import com.example.surfafisha.Models.Film

class ListFilmObserver(private val context: Context, private val filmAdapter: ListFilmAdapter) : Observer<List<Film>> {

    override fun onChanged(data: List<Film>?) {
        data?.forEach { film ->
            Glide.with(context)
                .asBitmap()
                .load(film.poster_path)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        filmAdapter.addElement(Pair(film, resource))
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        TODO("Not yet implemented")
                    }

                })
        }
    }
}