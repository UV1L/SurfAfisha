package com.example.surfafisha.VM

import androidx.lifecycle.*
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

class MainViewModel(db: FilmDataBase) : ViewModel(), IObservable {

    companion object {
        private const val baseUrl = "https://api.themoviedb.org/3/"
        private const val api_key = "6ccd72a2a8fc239b13f209408fc31c33"
        private const val lang = "ru-RU"
    }

    override val observers = ArrayList<IObserver>()
    private val roomDB: FilmDataBase = db
    private val _data: MutableLiveData<ArrayList<Film>> = MutableLiveData()
    private val _readyToShow: MutableLiveData<Boolean> = MutableLiveData()
    private val _clearAdapter: MutableLiveData<Boolean> = MutableLiveData()
    val data: LiveData<ArrayList<Film>> = _data
    val readyToShow: LiveData<Boolean> = _readyToShow
    val clearAdapter: LiveData<Boolean> = _clearAdapter

    init {
        _data.value = ArrayList()
        _readyToShow.value = false
        _clearAdapter.value = false
    }

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val filmsApi: FilmsApi = retrofit.create(FilmsApi::class.java)

    fun startQuery() {
        _clearAdapter.value = true
        _data.value = ArrayList()
        viewModelScope.launch(Dispatchers.IO) {
            synchronized(_data) {
                synchronized(_clearAdapter) {
                    val films: Call<FilmResponsePOJO> = filmsApi.getFilms(api_key, lang)

                    films.enqueue(object : Callback<FilmResponsePOJO> {
                        override fun onFailure(call: Call<FilmResponsePOJO>, t: Throwable) {
                            sendUpdateEvent(NoDataFragment.newInstance())
                        }

                        override fun onResponse(
                            call: Call<FilmResponsePOJO>,
                            filmResponse: Response<FilmResponsePOJO>
                        ) {
                            if (filmResponse.isSuccessful) {
                                onResponseSuccess(filmResponse)
                            }
                        }
                    })
                }
            }
        }
    }

    private fun onResponseSuccess(filmResponse: Response<FilmResponsePOJO>) {
        filmResponse.body()?.results?.forEach { filmPojo ->
            val filmsWithId = roomDB.filmDao().getAllByIds(
                intArrayOf(filmPojo.id)
            )

            val isFavorite = if (filmsWithId.isNotEmpty()) {
                filmsWithId.first().film!!.favorite
            } else {
                false
            }

            val film = FilmFactory.createFilmModel(filmPojo, isFavorite)
            postValueData(film)
        }
        _readyToShow.postValue(true)
    }

    fun postValueData(film: Film) {
        val dataValue = _data.value
        dataValue?.add(film)
        _data.postValue(dataValue)
    }

    fun startFilterQuery(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            synchronized(_data) {
                synchronized(_clearAdapter) {
                    val films: Call<FilmResponsePOJO> =
                        filmsApi.getFilteredFilms(api_key, lang, query)

                    films.enqueue(object : Callback<FilmResponsePOJO> {
                        override fun onFailure(call: Call<FilmResponsePOJO>, t: Throwable) {
                            sendUpdateEvent(NothingFindFragment.newInstance())
                        }

                        override fun onResponse(
                            call: Call<FilmResponsePOJO>,
                            filmResponse: Response<FilmResponsePOJO>
                        ) {
                            if (filmResponse.isSuccessful)
                                onResponseSuccessFilter(filmResponse)
                        }
                    })
                }
            }
        }
    }

    private fun onResponseSuccessFilter(filmResponse: Response<FilmResponsePOJO>) {
        _clearAdapter.value = true
        _data.value = ArrayList()
        filmResponse.body()?.results?.forEach { filmPojo ->
            val filmsWithId = roomDB.filmDao().getAllByIds(
                intArrayOf(filmPojo.id)
            )

            val isFavorite = if (filmsWithId.isNotEmpty()) {
                filmsWithId.first().film!!.favorite
            } else {
                false
            }

            val film = FilmFactory.createFilmModel(filmPojo, isFavorite)
            postValueData(film)
            _clearAdapter.postValue(true)
        }
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