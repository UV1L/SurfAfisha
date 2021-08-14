package com.example.data.Models

import com.example.data.POJO.FilmPOJO

class FilmFactory {

    companion object {
        private const val posterBaseUrl = "https://image.tmdb.org/t/p/w185"

        fun createFilmModel(film: FilmPOJO, favorite: Boolean) : FilmNet {
            val posterPath = posterBaseUrl + film.poster_path
            film.overview?.let {
                film.release_date?.let {
                    return FilmNet(
                        film.id,
                        film.title,
                        posterPath,
                        film.overview!!,
                        favorite,
                        film.release_date!!
                    )
                }
            }
            return FilmNet(
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