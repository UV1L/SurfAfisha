package com.example.data

import com.example.data.POJO.FilmPOJO
import com.example.domain.model.Film

class FilmFromNetMapper : (FilmPOJO) -> Film {

    override fun invoke(responseNet: FilmPOJO): Film {

        return Film(
            id = responseNet.id,
            title = responseNet.title,
            poster_path = responseNet.poster_path,
            overview = responseNet.overview.orEmpty(),
            favorite = false,
            release_date = responseNet.release_date.orEmpty()
        )
    }
}