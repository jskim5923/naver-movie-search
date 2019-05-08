package com.example.movie_search.api

import com.example.movie_search.model.Movie
import com.google.gson.annotations.SerializedName

data class SearchMovieResponse(
    @SerializedName("total") val total: Int,
    @SerializedName("items") val items: List<Movie>
)