package com.example.movie_search.repository

import com.example.movie_search.BuildConfig.NAVER_API_CLIENT_ID
import com.example.movie_search.BuildConfig.NAVER_API_CLIENT_SECRET
import com.example.movie_search.api.SearchApi
import com.example.movie_search.api.SearchMovieResponse
import io.reactivex.Single

class NetworkRepository(private val api: SearchApi) {
    fun getMovieList(keyword: String): Single<SearchMovieResponse> {
        return api.getMovieInfoList(
            NAVER_API_CLIENT_ID,
            NAVER_API_CLIENT_SECRET,
            keyword
        )
    }
}