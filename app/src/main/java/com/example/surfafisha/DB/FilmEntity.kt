package com.example.surfafisha.DB

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.surfafisha.Models.Film

@Entity
data class FilmEntity(
    @PrimaryKey val filmId: Int,
    @Embedded val film: Film?
)