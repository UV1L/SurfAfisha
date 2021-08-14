package com.example.domain.use_cases

import com.example.domain.FilmRepository
import com.example.domain.model.Film

class LoadFilmsUseCaseImpl(private val filmRepository: FilmRepository) : LoadFilmsUseCase {

    override suspend fun execute(): List<Film> {

        val films = filmRepository.loadFilms()

        films
    }
}