package com.example.surfafisha.DB.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import com.example.surfafisha.DB.FilmEntity

@Dao
interface FilmDAO {
    @Query("SELECT * FROM filmentity")
    fun getAll(): List<FilmEntity>

    @Query("SELECT * FROM filmentity WHERE id in (:ids)")
    fun getAllByIds(ids: IntArray): List<FilmEntity>

    @Delete
    fun delete(filmEntity: FilmEntity)
}