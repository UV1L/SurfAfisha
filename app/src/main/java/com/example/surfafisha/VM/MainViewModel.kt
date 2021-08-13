package com.example.surfafisha.VM

import androidx.lifecycle.*
import com.example.surfafisha.BaseViewModel
import com.example.surfafisha.ChangeColorStatusBarViewEvent
import com.example.surfafisha.DB.DAO.FilmDataBase
import com.example.surfafisha.DB.FilmEntity
import com.example.surfafisha.IObservable
import com.example.surfafisha.IObserver
import com.example.surfafisha.Models.Film
import com.example.surfafisha.Models.FilmFactory
import com.example.surfafisha.POJO.FilmResponsePOJO
import com.example.surfafisha.POJO.FilmsApi
import com.example.surfafisha.ui.main.Fragments.NoDataFragment
import com.example.surfafisha.ui.main.Fragments.NothingFindFragment
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.collections.ArrayList

class MainViewModel(db: FilmDataBase) : BaseViewModel(), IObservable {

    companion object {
        private const val baseUrl = "https://api.themoviedb.org/3/"
        private const val api_key = "6ccd72a2a8fc239b13f209408fc31c33"
        private const val lang = "ru-RU"
    }

    override val observers = ArrayList<IObserver>()
    private val roomDB: FilmDataBase = db
    private val _data: MutableLiveData<List<Film>> = MutableLiveData()
    private val _readyToShow: MutableLiveData<Boolean> = MutableLiveData()
    val data: LiveData<List<Film>> = _data
    val readyToShow: LiveData<Boolean> = _readyToShow

    private var queryJob: Job? = null

    init {
        _data.value = ArrayList()
        _readyToShow.value = false
    }

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val filmsApi: FilmsApi = retrofit.create(FilmsApi::class.java)

    private fun updateFilms(films: List<Film>) = _data.postValue(films)

    fun startQuery(filter: String? = null) {
        queryJob?.cancel()
        updateFilms(emptyList())
        queryJob = viewModelScope.launch(Dispatchers.IO) {

            val response = when (filter) {

                null -> filmsApi.getFilms(api_key, lang)
                else ->  filmsApi.getFilteredFilms(api_key, lang, filter)
            }

            response.enqueue(object : Callback<FilmResponsePOJO> {
                    override fun onFailure(call: Call<FilmResponsePOJO>, t: Throwable) {
                        sendUpdateEvent(NothingFindFragment.newInstance())
                    }

                    override fun onResponse(
                        call: Call<FilmResponsePOJO>,
                        filmResponse: Response<FilmResponsePOJO>
                    ) {
                        if (filmResponse.isSuccessful)
                            onResponseSuccess(filmResponse)
                    }
                })
        }
    }

    private fun onResponseSuccess(filmResponse: Response<FilmResponsePOJO>) {

        val filmsFromDb = roomDB.filmDao()
            .getAllByIds(filmResponse.body()?.results?.map { it.id }.orEmpty().toIntArray())

        val newFilms = filmResponse.body()!!.results.map { filmPOJO ->

            val filmFromDb = filmsFromDb.firstOrNull() { it.film!!.id == filmPOJO.id }
            val ifFavorite = filmFromDb?.film?.favorite ?: false
            FilmFactory.createFilmModel(filmPOJO, ifFavorite)
        }

        updateFilms(newFilms)
        _readyToShow.postValue(true)
    }

    fun dbUpdate(film: Film) {
        viewModelScope.launch(Dispatchers.IO) {
            val filmWithId = roomDB.filmDao().getAllByIds(intArrayOf(film.id))
            if (filmWithId.isNotEmpty()) {
                val primaryKey = filmWithId.first().filmId
                val filmEntity = FilmEntity(primaryKey, film)
                roomDB.filmDao().updateAll(filmEntity)
            } else {
                val allFilms = roomDB.filmDao().getAll()
                roomDB.filmDao().insertAll(FilmEntity(allFilms.size + 1, film))
            }
        }
    }

    fun clearData() {
        _data.value = ArrayList()
    }
}