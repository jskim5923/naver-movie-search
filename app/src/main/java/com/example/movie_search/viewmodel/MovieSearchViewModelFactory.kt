package com.example.movie_search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movie_search.repository.NetworkRepositoryImpl

@Suppress("UNCHECKED_CAST")
class MovieSearchViewModelFactory(private val repository: NetworkRepositoryImpl): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MovieSearchViewModel(repository) as T
    }
}