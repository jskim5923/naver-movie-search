package com.example.movie_search.api

import com.example.movie_search.model.Movie

data class SearchMovieResponse(val total: Int, val items: List<Movie>)