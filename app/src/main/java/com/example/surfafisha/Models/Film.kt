package com.example.surfafisha.Models

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class Film(
    @SerializedName("id") var id: Int,
    @SerializedName("title") var title: String,
    @SerializedName("poster_path") var poster_path: String,
    @SerializedName("overview") var overview: String,
    @SerializedName("favorite") var favorite: Boolean,
    @SerializedName("release_date") var release_date: String
)