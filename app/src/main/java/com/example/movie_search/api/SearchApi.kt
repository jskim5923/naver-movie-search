package com.example.movie_search.api

import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


interface SearchApi {
    companion object {
        val BASE_URL = "https://openapi.naver.com/"

        fun create(): SearchApi {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }).build())
                .build()
                .create(SearchApi::class.java)
        }
    }

    @GET("v1/search/movie.json")
    fun getMovieInfoList(
        @Header("X-Naver-Client-Id") id: String,
        @Header("X-Naver-Client-Secret") secretKey: String,
        @Query("query") searchText: String,
        @Query("display") displayCount:Int=20
    ): Single<SearchMovieResponse>
}