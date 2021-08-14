package com.example.domain.use_cases

import com.example.domain.model.Film
import kotlinx.coroutines.flow.Flow

interface LoadFilmsUseCase {

    suspend fun execute(): List<Film>
}