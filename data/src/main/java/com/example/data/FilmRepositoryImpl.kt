package com.example.data

import com.example.data.DB.DAO.FilmDataBase
import com.example.data.DB.DbProvider
import com.example.data.DB.FilmEntity
import com.example.data.Models.FilmNet
import com.example.data.POJO.FilmsApi
import com.example.domain.FilmRepository
import com.example.domain.model.Film
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

internal class FilmRepositoryImpl(

    dbProvider: DbProvider,
    private val filmMapper: FilmFromNetMapper

) : FilmRepository {

    private companion object {

        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val API_KEY = "6ccd72a2a8fc239b13f209408fc31c33"
        const val LANG = "ru-RU"
    }

    private val roomDB: FilmDataBase? = dbProvider.provide()

    private val api: FilmsApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(FilmsApi::class.java)

    override suspend fun loadFilms(): List<Film> {


        val films = api.getFilms(
             api_key = API_KEY,
             language = LANG
        ).execute().body()?.results?.map(filmMapper)!!

        val dbFilms = roomDB?.filmDao()?.getAll()?.toMutableList()
        dbFilms?.let {

            films.forEach {

                if (!dbFilms.contains(it) && !dbFilms.contains(
                        Film(
                            it.id,
                            it.title,
                            it.poster_path,
                            it.overview,
                            !it.favorite,
                            it.release_date)
                    ))
                        dbFilms.add(it)
            }
        }

        val filmEntities = dbFilms?.map {

            FilmEntity(it)
        }


        if (filmEntities != null) {
            roomDB?.filmDao()?.updateAll(filmEntities)
        }

        return dbFilms!!
    }

    suspend fun dbUpdate(coroutineContext: CoroutineContext, films: List<Film>) {
        withContext(coroutineContext) {

            this.launch {

                val filmEntities = films.map {

                    FilmEntity(it)
                }

                roomDB?.filmDao()?.updateAll(filmEntities)
            }
        }
    }

//    suspend fun dbUpdate(filmNet: FilmNet, coroutineContext: CoroutineContext) {
//        if (roomDB != null)
//            withContext(coroutineContext) {
//                this.launch {
//                    val filmWithId = roomDB.filmDao().getAllByIds(intArrayOf(filmNet.id))
//                    if (filmWithId.isNotEmpty()) {
//                        val primaryKey = filmWithId.first().filmId
//                        val filmEntity = FilmEntity(primaryKey, filmNet)
//                        roomDB.filmDao().updateAll(filmEntity)
//                    } else {
//                        val allFilms = roomDB.filmDao().getAll()
//                        roomDB.filmDao().insertAll(FilmEntity(allFilms.size + 1, filmNet))
//                    }
//                }
//            }
//    }
}