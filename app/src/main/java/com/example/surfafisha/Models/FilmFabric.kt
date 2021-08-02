package com.example.surfafisha.Models

import com.example.surfafisha.POJO.FilmPOJO

class FilmFabric {

    companion object {
        private const val posterBaseUrl = "https://image.tmdb.org/t/p/w185"

        fun createFilmModel(film: FilmPOJO, favorite: Boolean) : Film {
            val poster_path = posterBaseUrl + film.poster_path
            return Film(film.id, film.title, poster_path, film.overview, favorite, film.release_date)
        }
    }
}