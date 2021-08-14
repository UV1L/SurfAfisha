package com.example.domain.di

import com.example.domain.FilmRepository
import com.example.domain.use_cases.LoadFilmsUseCase
import com.example.domain.use_cases.LoadFilmsUseCaseImpl

class UseCaseModule(private val filmRepository: FilmRepository) {

    fun provideLoadFilmsUseCase(): LoadFilmsUseCase = LoadFilmsUseCaseImpl(
        filmRepository = filmRepository
    )
}