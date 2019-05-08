package com.example.movie_search.di

import com.example.movie_search.BuildConfig
import com.example.movie_search.api.SearchApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val apiModule: Module = module {
    single {
        Retrofit.Builder()
            .baseUrl(SearchApi.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(get(named("loggingInterceptor")))
                    .addInterceptor(get(named("authInterceptor")))
                    .build()
            )
            .build()
            .create(SearchApi::class.java)
    }

    single(named("loggingInterceptor")) {
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    single(named("authInterceptor")) {
        Interceptor { chain ->
            val builder = chain.request().newBuilder().apply {
                header("X-Naver-Client-Id", BuildConfig.NAVER_API_CLIENT_ID)
                header("X-Naver-Client-Secret", BuildConfig.NAVER_API_CLIENT_SECRET)
            }
            chain.proceed(builder.build())
        }
    }
}