package com.example.movie_search

import android.app.Application
import com.example.movie_search.di.apiModule
import com.example.movie_search.di.movieSearchModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            if(BuildConfig.DEBUG) {
                androidLogger()
            }
            androidContext(this@BaseApplication)
            modules(listOf(apiModule, movieSearchModule))
        }
    }
}