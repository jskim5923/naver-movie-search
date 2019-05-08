package com.example.movie_search.di

import com.example.movie_search.repository.NetworkRepositoryImpl
import com.example.movie_search.viewmodel.MovieSearchViewModelFactory
import org.koin.core.module.Module
import org.koin.dsl.module

val movieSearchModule: Module = module {
    factory {
        NetworkRepositoryImpl(get())
    }

    factory {
        MovieSearchViewModelFactory(get())
    }
}