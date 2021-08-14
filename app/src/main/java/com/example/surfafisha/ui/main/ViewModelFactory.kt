package com.example.surfafisha.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.data.DB.DAO.FilmDataBase
import com.example.data.RepositoryModule
import com.example.domain.FilmRepository
import com.example.domain.di.UseCaseModule
import com.example.domain.use_cases.LoadFilmsUseCase
import com.example.surfafisha.VM.MainViewModel

class ViewModelFactory() : ViewModelProvider.Factory {

    private val di = UseCaseModule(
        filmRepository = RepositoryModule().provideFilmRepository()
    )

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(
            loadFilmsUseCase = di.provideLoadFilmsUseCase()
        ) as T
    }
}