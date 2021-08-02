package com.example.surfafisha.POJO

import com.example.surfafisha.POJO.FilmResponsePOJO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FilmsApi {
    @GET("discover/movie")
    fun getFilms(
        @Query("api_key") api_key: String,
        @Query("language") language: String
    ): Call<FilmResponsePOJO>

    @GET("search/movie")
    fun getFilteredFilms(
        @Query("api_key") api_key: String,
        @Query("language") language: String,
        @Query("query") query: String
    ): Call<FilmResponsePOJO>
}