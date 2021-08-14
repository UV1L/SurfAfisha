package com.example.domain

import com.example.domain.model.Film

interface FilmRepository {

    suspend fun loadFilms(): List<Film>
}