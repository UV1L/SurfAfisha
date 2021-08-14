package com.example.data.DB.DAO

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.DB.FilmEntity

@Database(entities = [FilmEntity::class], version = 1)
abstract class FilmDataBase : RoomDatabase() {
    abstract fun filmDao(): FilmDAO
}