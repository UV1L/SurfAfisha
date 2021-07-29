package com.example.surfafisha.ui.main

data class FilmResponse(
    var page: Int?,
    var results:  Array<Film>,
    var total_results: Int?,
    var total_pages: Int?) {
}