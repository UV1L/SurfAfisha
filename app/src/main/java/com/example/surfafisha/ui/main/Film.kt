package com.example.surfafisha.ui.main

data class Film(
    var poster_path: String?,
    var adult: Boolean?,
    var overview: String?,
    var release_date: String?,
    var genre_ids: Array<Int>?,
    var id: Int?,
    var original_title: String?,
    var original_language: String?,
    var title: String?,
    var backdrop_path: String?,
    var popularity: Number?,
    var vote_count: Int?,
    var video: Boolean?,
    var vote_average: Number?
    ) {
}