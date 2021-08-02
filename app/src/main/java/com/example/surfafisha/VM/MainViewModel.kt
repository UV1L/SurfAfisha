package com.example.surfafisha.VM

import android.content.ContentProvider
import android.content.ContentValues
import androidx.lifecycle.*
import com.example.surfafisha.DB.FilmsDB
import com.example.surfafisha.Models.Film
import com.example.surfafisha.Models.FilmFabric
import com.example.surfafisha.POJO.FilmPOJO
import com.example.surfafisha.POJO.FilmResponsePOJO
import com.example.surfafisha.POJO.FilmsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModel(db: FilmsDB) : ViewModel() {

    companion object {
        private const val baseUrl = "https://api.themoviedb.org/3/"
        private const val api_key = "6ccd72a2a8fc239b13f209408fc31c33"
        private const val lang = "ru-RU"
    }

    private val _data: MutableLiveData<ArrayList<Film>> = MutableLiveData()
    val data: LiveData<ArrayList<Film>> = _data
    private val favoriteIds: HashSet<Int> = HashSet()
    private val filmDB = db.writableDatabase

    init {
        _data.value = ArrayList()
    }

    private val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    private val filmsApi: FilmsApi = retrofit.create(FilmsApi::class.java)

    fun parseJson() {
        viewModelScope.launch(Dispatchers.IO) {
            val films: Call<FilmResponsePOJO> = filmsApi.getFilms(api_key, lang)

            films.enqueue(object : Callback<FilmResponsePOJO> {
                override fun onFailure(call: Call<FilmResponsePOJO>, t: Throwable) {
                    print(t.message)
                    print(t.stackTrace)
                }

                override fun onResponse(call: Call<FilmResponsePOJO>, filmResponse: Response<FilmResponsePOJO>) {
                    if (filmResponse.isSuccessful)
                        filmResponse.body()?.results?.forEach {
                            val cv = ContentValues()
                            cv.put("filmId", it.id)
                            cv.put("favorite", 1)
                            filmDB.insert("filmsDB", null, cv)

                            var film: Film? = null
                            val cursor = filmDB.query("filmsDB", null, null, null, null, null, null)
                            if (cursor.moveToFirst()) {
                                val id = cursor.getInt(1)
                                val favorite = cursor.getInt(2)

                                do {
                                    if (it.id == id && favorite == 1)
                                        film = FilmFabric.createFilmModel(it, true)
                                } while (cursor.moveToNext())
                            }

                            if (film != null) {
                                val dataValue = _data.value
                                dataValue?.add(film)
                                _data.postValue(dataValue)
                            }
                        }
                }
            })
        }
    }
}