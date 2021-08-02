package com.example.surfafisha.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.surfafisha.DB.DAO.FilmDataBase
import com.example.surfafisha.VM.MainViewModel

class ViewModelFactory(private val db: FilmDataBase) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(db) as T
    }
}