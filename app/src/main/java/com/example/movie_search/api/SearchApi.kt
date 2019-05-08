package com.example.movie_search.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


interface SearchApi {
    companion object {
        val BASE_URL = "https://openapi.naver.com/"
    }
    @GET("v1/search/movie.json")
    fun getMovieInfoList(        @Query("query") searchText: String,
        @Query("display") displayCount: Int = 20
    ): Single<SearchMovieResponse>
}