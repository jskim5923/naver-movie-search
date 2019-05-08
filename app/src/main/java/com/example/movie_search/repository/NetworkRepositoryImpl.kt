package com.example.movie_search.repository

import com.example.movie_search.api.SearchApi
import com.example.movie_search.api.SearchMovieResponse
import io.reactivex.Single

class NetworkRepositoryImpl(private val api: SearchApi) {
    fun getMovieList(keyword: String): Single<SearchMovieResponse> {
        return api.getMovieInfoList(keyword)
    }
}