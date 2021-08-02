package com.example.surfafisha.POJO

import com.example.surfafisha.POJO.FilmPOJO
import com.google.gson.annotations.SerializedName

data class FilmResponsePOJO(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: List<FilmPOJO>,
    @SerializedName("total_results") val total_results: Int,
    @SerializedName("total_pages") val total_pages: Int) {
}