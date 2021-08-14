package com.example.surfafisha.VM

import androidx.lifecycle.*
import com.example.surfafisha.BaseViewModel
import com.example.data.DB.DAO.FilmDataBase
import com.example.data.DB.FilmEntity
import com.example.data.Models.FilmNet
import com.example.data.Models.FilmFactory
import com.example.data.POJO.FilmResponsePOJO
import com.example.data.POJO.FilmsApi
import com.example.domain.model.Film
import com.example.domain.use_cases.LoadFilmsUseCase
import com.example.surfafisha.ui.main.Fragments.NothingFindFragment
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlin.collections.ArrayList

class MainViewModel(private val loadFilmsUseCase: LoadFilmsUseCase) : BaseViewModel() {

    private val _data: MutableLiveData<List<Film>> = MutableLiveData()
    private val _readyToShow: MutableLiveData<Boolean> = MutableLiveData()
    val data: LiveData<List<Film>> = _data
    val readyToShow: LiveData<Boolean> = _readyToShow

    init {
        _data.value = ArrayList()
        _readyToShow.value = false
    }

    fun getData() {
        viewModelScope.launch {

            _data.value = loadFilmsUseCase.execute()
        }
    }

//    fun startQuery(filter: String? = null) {
//        queryJob?.cancel()
//        updateFilms(emptyList())
//        queryJob = viewModelScope.launch(Dispatchers.IO) {
//
//            val response = when (filter) {
//
//                null -> filmsApi.getFilms(api_key, lang)
//                else ->  filmsApi.getFilteredFilms(api_key, lang, filter)
//            }
//
//            response.enqueue(object : Callback<FilmResponsePOJO> {
//                    override fun onFailure(call: Call<FilmResponsePOJO>, t: Throwable) {
//                        sendUpdateEvent(NothingFindFragment.newInstance())
//                    }
//
//                    override fun onResponse(
//                        call: Call<FilmResponsePOJO>,
//                        filmResponse: Response<FilmResponsePOJO>
//                    ) {
//                        if (filmResponse.isSuccessful)
//                            onResponseSuccess(filmResponse)
//                    }
//                })
//        }
//    }

//    private fun onResponseSuccess(filmResponse: Response<FilmResponsePOJO>) {
//
//        val filmsFromDb = roomDB.filmDao()
//            .getAllByIds(filmResponse.body()?.results?.map { it.id }.orEmpty().toIntArray())
//
//        val newFilms = filmResponse.body()!!.results.map { filmPOJO ->
//
//            val filmFromDb = filmsFromDb.firstOrNull() { it.filmNet!!.id == filmPOJO.id }
//            val ifFavorite = filmFromDb?.filmNet?.favorite ?: false
//            FilmFactory.createFilmModel(filmPOJO, ifFavorite)
//        }
//
//        updateFilms(newFilms)
//        _readyToShow.postValue(true)
//    }

    fun clearData() {
        _data.value = ArrayList()
    }
}