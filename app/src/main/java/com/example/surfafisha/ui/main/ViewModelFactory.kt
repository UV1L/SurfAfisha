package com.example.surfafisha.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.surfafisha.DB.FilmsDB
import com.example.surfafisha.VM.MainViewModel

class ViewModelFactory(private val db: FilmsDB) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(db) as T
    }
}