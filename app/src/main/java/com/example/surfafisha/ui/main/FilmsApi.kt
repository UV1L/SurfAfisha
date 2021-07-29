package com.example.surfafisha.ui.main

import retrofit2.Call
import retrofit2.http.GET

interface FilmsApi {
    @GET("discover/movie")
    fun getFilms(): Call<FilmResponse>
}