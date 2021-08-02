package com.example.surfafisha.DB.DAO

import androidx.room.*
import com.example.surfafisha.DB.FilmEntity
import com.example.surfafisha.Models.Film

@Dao
interface FilmDAO {
    @Query("SELECT * FROM filmentity")
    fun getAll(): List<Film>

    @Query("SELECT * FROM filmentity WHERE id in (:ids)")
    fun getAllByIds(ids: IntArray): List<FilmEntity>

    @Insert
    fun insertAll(vararg film: FilmEntity)

    @Update
    fun updateAll(vararg film: FilmEntity)

    @Delete
    fun delete(filmEntity: FilmEntity)
}