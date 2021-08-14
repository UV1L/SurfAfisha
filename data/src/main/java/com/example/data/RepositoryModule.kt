package com.example.data

import com.example.data.DB.DbProviderImpl
import com.example.domain.FilmRepository

class RepositoryModule {

    fun provideFilmRepository(): FilmRepository = FilmRepositoryImpl(

        dbProvider = DbProviderImpl,
        filmMapper = FilmFromNetMapper()
    )
}