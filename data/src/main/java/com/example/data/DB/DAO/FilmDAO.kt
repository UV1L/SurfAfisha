package com.example.data.DB.DAO

import androidx.room.*
import com.example.data.DB.FilmEntity
import com.example.domain.model.Film

@Dao
interface FilmDAO {
    @Query("SELECT * FROM filmentity")
    fun getAll(): List<Film>

    @Query("SELECT * FROM filmentity WHERE id in (:ids)")
    fun getAllByIds(ids: IntArray): List<FilmEntity>

    @Insert
    fun insertAll(films: List<FilmEntity>)

    @Update
    fun updateAll(films: List<FilmEntity>)

    @Delete
    fun delete(filmEntity: FilmEntity)
}