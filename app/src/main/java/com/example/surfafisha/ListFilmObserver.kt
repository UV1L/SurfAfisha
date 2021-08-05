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

class ListFilmObserver(private val context: Context, private val filmAdapter: ListFilmAdapter) :
    Observer<List<Film>>, IObservable {

    override val observers: ArrayList<IObserver> = ArrayList()

    override fun onChanged(data: List<Film>?) {
        data?.forEach { film ->
            if (film.poster_path.contains("null"))
                filmAdapter.addElement(
                    Pair(film, Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888))
                )
            else {
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
        if (data?.isNotEmpty() == true) {
            sendUpdateEvent(this)
        }
    }
}