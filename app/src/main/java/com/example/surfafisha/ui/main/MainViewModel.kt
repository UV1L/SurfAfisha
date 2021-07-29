package com.example.surfafisha.ui.main

import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModel : ViewModel() {

    companion object {
        private const val baseUrl = "https://developers.themoviedb.org/3/"
    }

    var data: ArrayList<String> = ArrayList()
    private val gson: Gson = GsonBuilder().setLenient().create()
    
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val filmsApi: FilmsApi = retrofit.create(FilmsApi::class.java)

    fun parseJson() {
        val films: Call<FilmResponse> = filmsApi.getFilms()

        films.enqueue(object : Callback<FilmResponse> {
            override fun onFailure(call: Call<FilmResponse>, t: Throwable) {
                print(t.message)
                print(t.stackTrace)
            }

            override fun onResponse(call: Call<FilmResponse>, filmResponse: Response<FilmResponse>) {
                filmResponse.body()?.let { res ->
                    res.results?.forEach {film ->
//                        data.add(film.original_title)
                    }
                }
            }
        })
    }
}