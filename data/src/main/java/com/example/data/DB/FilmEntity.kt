package com.example.data.DB

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.Models.FilmNet
import com.example.domain.model.Film

@Entity
data class FilmEntity(

    @PrimaryKey(autoGenerate = true)
    @Embedded val film: Film?
)