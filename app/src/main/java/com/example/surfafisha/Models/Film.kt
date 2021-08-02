package com.example.surfafisha.Models

import com.google.gson.annotations.SerializedName

data class Film(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("poster_path") val poster_path: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("favorite") val favorite: Boolean,
    @SerializedName("release_date") val release_date: String
)