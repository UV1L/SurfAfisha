package com.example.data.DB

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.data.DB.DAO.FilmDataBase
import java.lang.ref.WeakReference

interface DbProvider {

    companion object {

        fun initialize(context: Context): DbProvider = DbProviderImpl.apply {

            initialize(context)
        }
    }

    fun provide(): FilmDataBase?
}

internal object DbProviderImpl : DbProvider {

    private lateinit var context: WeakReference<Context>

    @Volatile
    var DB: FilmDataBase? = null

    fun initialize(context: Context) {

        this.context = WeakReference(context)
    }

    @Synchronized
    override fun provide(): FilmDataBase? {

        runCatching {

            return DB ?: Room.databaseBuilder(
                context.get()!!,
                FilmDataBase::class.java,
                "FilmReader"
            ).allowMainThreadQueries().build()

        }.onFailure {

            Log.d("com.example.data.DB", "not find context")
        }

        return null
    }
}