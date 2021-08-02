package com.example.surfafisha.Models

import com.example.surfafisha.POJO.FilmPOJO

class FilmFactory {

    companion object {
        private const val posterBaseUrl = "https://image.tmdb.org/t/p/w185"

        fun createFilmModel(film: FilmPOJO, favorite: Boolean) : Film {
            val posterPath = posterBaseUrl + film.poster_path
            film.overview?.let {
                film.release_date?.let {
                    return Film(
                        film.id,
                        film.title,
                        posterPath,
                        film.overview!!,
                        favorite,
                        film.release_date!!
                    )
                }
            }
            return Film(
                film.id,
                film.title,
                posterPath,
                "",
                favorite,
                "Неизвестно"
            )
        }
    }
}