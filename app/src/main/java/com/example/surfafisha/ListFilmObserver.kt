package com.example.surfafisha

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import androidx.core.graphics.drawable.toDrawable
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.surfafisha.Adapters.ListFilmAdapter
import com.example.surfafisha.Models.Film
import java.util.*
import kotlin.collections.ArrayList

class ListFilmObserver(private val filmAdapter: ListFilmAdapter) :
    Observer<List<Film>>, IObservable {

    override val observers: ArrayList<IObserver> = ArrayList()

    override fun onChanged(data: List<Film>?) {
        filmAdapter.clear()
        data?.let { filmAdapter.addAll(it) }
    }
}