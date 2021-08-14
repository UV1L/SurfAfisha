package com.example.surfafisha

import androidx.lifecycle.Observer
import com.example.surfafisha.Adapters.ListFilmAdapter
import com.example.data.Models.FilmNet
import com.example.domain.IObservable
import com.example.domain.IObserver
import com.example.domain.model.Film
import kotlin.collections.ArrayList

class ListFilmObserver(private val filmAdapter: ListFilmAdapter) : Observer<List<Film>> {

    override fun onChanged(data: List<Film>?) {
        data?.let { filmAdapter.addAll(it) }
    }
}