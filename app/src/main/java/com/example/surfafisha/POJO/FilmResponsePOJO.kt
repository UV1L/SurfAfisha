package com.example.surfafisha.POJO

import com.example.surfafisha.POJO.FilmPOJO
import com.google.gson.annotations.SerializedName

data class FilmResponsePOJO(
    @SerializedName("page") var page: Int,
    @SerializedName("results") var results: List<FilmPOJO>,
    @SerializedName("total_results") var total_results: Int,
    @SerializedName("total_pages") var total_pages: Int) {
}